package cat.reisigualada.reis.web.arxiu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.model.lists.ExcelList;
import cat.reisigualada.reis.model.lists.PDFList;
import cat.reisigualada.reis.service.ClauService;
import cat.reisigualada.reis.service.DBUtils;
import cat.reisigualada.reis.service.FitxerService;
import cat.reisigualada.reis.utils.AjaxResponseBody;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.utils.FileUtils;
import cat.reisigualada.reis.utils.ListUtils;
import cat.reisigualada.reis.validator.FitxerValidator;

@Controller
public class ArxiuController {
    @Autowired
    private ClauService clauService;
    @Autowired
    private FitxerService fitxerService;
    @Autowired
    private FitxerValidator fitxerValidator;

    private static String UPLOADED_FOLDER = "D://temp//gd_reis1//";
    
    @RequestMapping(value = "/arxiu/consulta", method = RequestMethod.GET)
    public String consulta(Model model) {
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuConsultaActive", "active");
        
        return "/arxiu/consulta";
    }
    
    @RequestMapping(value = "/arxiu/consultaFitxers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody AjaxResponseBody consultaFitxers(@RequestBody SearchCriteriaFitxers criteria, BindingResult bindingResult, Model model) {
		AjaxResponseBody result = new AjaxResponseBody();
		
		if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
			// Convertim les paraules clau a Claus
	    	HashSet<Clau> hsC = new HashSet<Clau>();
	        for(String clau : criteria.getParaulesClau().split(",")){
	        	Clau c = clauService.findByNameAndType(clau.trim(), criteria.getTypeDocument());
	        	hsC.add(c);
	        }
	        criteria.setClaus(hsC);
		}
		try{
			result.setResult(DBUtils.searchByCriteria(criteria));
		} catch(Exception e){
			result.setMsg("S'ha produit un error recuperant les dades");
		}
		return result;
    }
    
    @RequestMapping(value = "/arxiu/downloadExcel", method = RequestMethod.GET)
    public void downloadExcel(Model model, String titol, Long year, Long typeDocument, String paraulesClau, HttpServletResponse response) throws Exception {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=fitxers.xlsx");
        
    	SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
    	criteria.setTitol(titol);
    	criteria.setYear(year);
    	criteria.setTypeDocument(typeDocument);
    	criteria.setParaulesClau(paraulesClau);
    	criteria.setSearchKeys(true);
    	if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
			// Convertim les paraules clau a Claus
	    	HashSet<Clau> hsC = new HashSet<Clau>();
	        for(String clau : criteria.getParaulesClau().split(",")){
	        	Clau c = clauService.findByNameAndType(clau.trim(), criteria.getTypeDocument());
	        	hsC.add(c);
	        }
	        criteria.setClaus(hsC);
		}
    	List<Fitxer> listFitxers = null;
		try{
			listFitxers = DBUtils.searchByCriteria(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}
    	
    	ExcelList.excelFitxers(response.getOutputStream(), listFitxers);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/arxiu/downloadPDF", method = RequestMethod.GET)
    public void downloadPDF(Model model, String titol, Long year, Long typeDocument, String paraulesClau, HttpServletResponse response) throws Exception {
    	response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=fitxers.pdf");
        
        SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
    	criteria.setTitol(titol);
    	criteria.setYear(year);
    	criteria.setTypeDocument(typeDocument);
    	criteria.setParaulesClau(paraulesClau);
    	criteria.setSearchKeys(true);
    	if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
			// Convertim les paraules clau a Claus
	    	HashSet<Clau> hsC = new HashSet<Clau>();
	        for(String clau : criteria.getParaulesClau().split(",")){
	        	Clau c = clauService.findByNameAndType(clau.trim(), criteria.getTypeDocument());
	        	hsC.add(c);
	        }
	        criteria.setClaus(hsC);
		}
    	List<Fitxer> listFitxers = null;
		try{
			listFitxers = DBUtils.searchByCriteria(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}    	

    	PDFList.pdfFitxers(response.getOutputStream(), listFitxers);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/arxiu/registre", method = RequestMethod.GET)
    public String registre(Model model, Long id) {
    	boolean editMode = false;
    	Fitxer fForm;
    	if(id!=null){
    		editMode = true;
    		fForm = fitxerService.findById(id);
        	model.addAttribute("fitxerForm", fForm);
        	// Claus relacionades amb el tipus de document seleccionat
        	List<Clau> lK = clauService.findByType(fForm.getTypeDocument());
        	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
    	} else {
    		fForm = new Fitxer();
        	fForm.setTypeDocument(Constants.TYPE_KEY_IMAGE);
        	model.addAttribute("fitxerForm", fForm);
        	// Claus relacionades amb el tipus de document per defecte
        	List<Clau> lK = clauService.findByType(fForm.getTypeDocument());
        	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
    	}
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuRegistreActive", "active");
    	model.addAttribute("editMode", editMode);        
        return "/arxiu/registre";
    }
    
    @RequestMapping(value = "/arxiu/create", method = RequestMethod.POST)
    public String create(@RequestParam("file") MultipartFile file, @ModelAttribute("fitxerForm") Fitxer fitxerForm, BindingResult bindingResult, Model model) {
    	boolean newFile = true;
    	if(fitxerForm.getId()!=null) newFile = false;
    	
    	// Validem el formulari
    	fitxerValidator.validate(fitxerForm, bindingResult);
    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	loadViewRegistre(model, fitxerForm, newFile);
        	return "/arxiu/registre";
        }
        
        // Només en les altes
        if(fitxerForm.getId()==null){
	    	if (file.isEmpty()) {
	        	model.addAttribute("messageError", "Cal seleccionar un fitxer");
	        	loadViewRegistre(model, fitxerForm, newFile);
	        	return "/arxiu/registre";
	        } else {
	        	// Obtenim el format
	        	fitxerForm.setFormat(FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase());
	        	// Generem el nom del fitxer
	        	fitxerForm.setFileName(FileUtils.generateFileName(fitxerForm));
	        }
        }

    	// Convertim les paraules clau a Claus
    	HashSet<Clau> hsC = new HashSet<Clau>();
        for(String clau : fitxerForm.getParaulesClau().split(",")){
        	Clau c = clauService.findByNameAndType(clau.trim(), fitxerForm.getTypeDocument());
        	if(c==null){
            	model.addAttribute("messageError", "La clau '" + clau + "' no existeix");
            	loadViewRegistre(model, fitxerForm, newFile);
	        	return "/arxiu/registre";
        	}
        	hsC.add(c);
        }
        fitxerForm.setClaus(hsC);
        
    	// Guardem el fitxer
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fitxerForm.getFileName());
            Files.write(path, bytes);
            model.addAttribute("messageOk", "Document registrat correctament " + fitxerForm.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("messageError", "El document " + fitxerForm.getFileName() + " no s'ha pogut crear al disc");
            loadViewRegistre(model, fitxerForm, newFile);
            return "/arxiu/registre";
        }

    	// Creem el nou fitxer
        try {
        	fitxerService.save(fitxerForm);
        } catch(Exception e){
        	e.printStackTrace();
        	model.addAttribute("messageError", "El document " + fitxerForm.getFileName() + " no s'ha pogut crear");
            loadViewRegistre(model, fitxerForm, newFile);
            return "/arxiu/registre";
        }
        
        // Parametres per poder carregar la vista
        loadViewRegistre(model, fitxerForm, newFile);
        return "/arxiu/registre";
    }
    
    private void loadViewRegistre(Model model, Fitxer fitxerForm, boolean newFile){
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuRegistreActive", "active");
        // Claus relacionades amb el tipus de document seleccionat
    	List<Clau> lK = clauService.findByType(fitxerForm.getTypeDocument());
    	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
        if(newFile){
        	fitxerForm.setId(null);
        	fitxerForm.setFormat(null);
        	fitxerForm.setFileName(null);
        	model.addAttribute("editMode", false);
        } else {
        	model.addAttribute("editMode", true);
        }
    }
    
    @RequestMapping(value = "/arxiu/eliminar", method = RequestMethod.GET)
    public String eliminar(Model model, Long id, Boolean searchOn, String titol, Long year, Long typeDocument, String paraulesClau) {
    	// Obtenim el fitxer
    	Fitxer f = fitxerService.findById(id);
    	
    	// Eliminem el fitxer fisic del disc
    	File file = new File(UPLOADED_FOLDER + f.getFileName());
    	if(file!=null){
			if(file.delete()){
				System.out.println(file.getName() + " esborrat");
			} else {
				System.out.println("Error esborrant el fitxer");
			}
    	}
    	// Eliminem el fitxer
    	fitxerService.deleteById(id);
    	
    	// Cerquem els resultats
    	if(searchOn!=null && searchOn){
    		SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
        	criteria.setTitol(titol);
        	criteria.setYear(year);
        	criteria.setTypeDocument(typeDocument);
        	criteria.setParaulesClau(paraulesClau);
        	criteria.setSearchKeys(true);
        	if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
    			// Convertim les paraules clau a Claus
    	    	HashSet<Clau> hsC = new HashSet<Clau>();
    	        for(String clau : criteria.getParaulesClau().split(",")){
    	        	Clau c = clauService.findByNameAndType(clau.trim(), criteria.getTypeDocument());
    	        	hsC.add(c);
    	        }
    	        criteria.setClaus(hsC);
    		}
        	List<Fitxer> listFitxers = null;
    		try{
    			listFitxers = DBUtils.searchByCriteria(criteria);
    		} catch(Exception e){ 
    			System.out.println("S'ha produit un error recuperant les dades");
    		}
    		model.addAttribute("searchOn", searchOn);
    		model.addAttribute("titol", titol);
    		model.addAttribute("year", year);
    		model.addAttribute("typeDocument", typeDocument);
    		// Cerquem les paraules clau
    		if(typeDocument!=null){
    			List<Clau> lK = clauService.findByType(typeDocument);
    			model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
    		}
    		model.addAttribute("paraulesClau", paraulesClau);
    		model.addAttribute("listFitxers", listFitxers);
    	} else {
    		
    	}
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuConsultaActive", "active");
        
    	return "/arxiu/consulta";
    }
}