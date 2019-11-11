package cat.reisigualada.reis.web.arxiu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import cat.reisigualada.reis.vo.FitxerKey;
import cat.reisigualada.reis.model.Autor;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.model.documentacio.ExcelUtils;
import cat.reisigualada.reis.model.documentacio.PDFUtils;
import cat.reisigualada.reis.service.AutorService;
import cat.reisigualada.reis.service.ClauService;
import cat.reisigualada.reis.service.DBUtils;
import cat.reisigualada.reis.service.FitxerService;
import cat.reisigualada.reis.utils.AjaxResponseBody;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.utils.FileUtils;
import cat.reisigualada.reis.utils.ListUtils;
import cat.reisigualada.reis.validator.ArxiuValidator;

@Controller
public class ArxiuController {
    @Autowired
    private ClauService clauService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private FitxerService fitxerService;
    @Autowired
    private ArxiuValidator arxiuValidator;

    private static String SESSION_SEARCH = "SearchCriteriaFitxersSession";
    
    @RequestMapping(value = "/arxiu/consulta", method = RequestMethod.GET)
    public String consulta(Model model, HttpServletRequest request) {
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuConsultaActive", "active");
        model.addAttribute("autorList", autorService.findAll());
        
        // Eliminamos los datos de la sessión
        request.getSession().removeAttribute(SESSION_SEARCH);
        
        return "/arxiu/consulta";
    }
    
    @RequestMapping(value = "/arxiu/consultaBack", method = RequestMethod.GET)
    public String consultaBack(Model model, HttpServletRequest request) {
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuConsultaActive", "active");
        model.addAttribute("autorList", autorService.findAll());
        
        SearchCriteriaFitxers criteria = null;
        try{ criteria = (SearchCriteriaFitxers)request.getSession().getAttribute(SESSION_SEARCH); } catch(Exception e){}
        if(criteria!=null){
        	model.addAttribute("fileName", criteria.getReferencia());
        	model.addAttribute("referencia", criteria.getReferenciaArxiu());
        	model.addAttribute("titol", criteria.getTitol());
        	model.addAttribute("year", criteria.getYear());
        	model.addAttribute("typeDocument", criteria.getTypeDocument());
        	if(criteria.getTypeDocument()!=null){
	        	// Claus relacionades amb el tipus de document seleccionat
	        	List<Clau> lK = clauService.findByType(criteria.getTypeDocument());
	        	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
	        	model.addAttribute("paraulesClau", criteria.getParaulesClau());
        	}
        	model.addAttribute("autor_id", criteria.getAutor_id());
        	model.addAttribute("pagina", criteria.getPagina());
        	model.addAttribute("searchOn", "true");
        }
        
        return "/arxiu/consulta";
    }
    
    @RequestMapping(value = "/arxiu/consultaFitxers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody AjaxResponseBody consultaFitxers(@RequestBody SearchCriteriaFitxers criteria, BindingResult bindingResult, Model model, HttpServletRequest request) {
		if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
			// Convertim les paraules clau a Claus
	    	HashSet<Clau> hsC = new HashSet<Clau>();
	        for(String clau : criteria.getParaulesClau().split(",")){
	        	Clau c = clauService.findByNameAndType(clau.trim(), criteria.getTypeDocument());
	        	hsC.add(c);
	        }
	        criteria.setClaus(hsC);
		}
		AjaxResponseBody result = new AjaxResponseBody();
		try{
			result = DBUtils.searchForView(criteria);
		} catch(Exception e){
			result.setMsg("S'ha produit un error recuperant les dades");
		}
		// Guardem els criteris de cerca a la sessió
		request.getSession().setAttribute(SESSION_SEARCH, criteria);
		return result;
    }
    
    @RequestMapping(value = "/arxiu/downloadExcel", method = RequestMethod.GET)
    public void downloadExcel(Model model, String referencia, String referenciaArxiu, String titol, Long year, Long typeDocument, String paraulesClau, Long autor, HttpServletResponse response) throws Exception {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=llistat_cerca.xlsx");
        
        SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
        criteria.setReferencia(referencia);
        criteria.setReferenciaArxiu(referenciaArxiu);
    	criteria.setTitol(titol);
    	criteria.setYear(year);
    	criteria.setTypeDocument(typeDocument);
    	criteria.setParaulesClau(paraulesClau);
    	criteria.setAutor_id(autor);
    	if(autor!=null){
    		Autor a = autorService.findById(autor);
        	criteria.setAutor(a.getName());
    	}
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
			listFitxers = DBUtils.searchForDocument(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}
    	
    	ExcelUtils.excelFitxers(response.getOutputStream(), listFitxers);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/arxiu/downloadPDF", method = RequestMethod.GET)
    public void downloadPDF(Model model, String referencia, String referenciaArxiu, String titol, Long year, Long typeDocument, String paraulesClau, Long autor, HttpServletResponse response) throws Exception {
    	response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=llistat_cerca.pdf");
        
        SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
        criteria.setReferencia(referencia);
        criteria.setReferenciaArxiu(referenciaArxiu);
    	criteria.setTitol(titol);
    	criteria.setYear(year);
    	criteria.setTypeDocument(typeDocument);
    	criteria.setParaulesClau(paraulesClau);
    	criteria.setAutor_id(autor);
    	if(autor!=null){
    		Autor a = autorService.findById(autor);
        	criteria.setAutor(a.getName());
    	}
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
			listFitxers = DBUtils.searchForDocument(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}    	

    	PDFUtils.pdfFitxers(response.getOutputStream(), criteria, listFitxers);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/arxiu/registre", method = RequestMethod.GET)
    public String registre(Model model, Long id, Long typeDocument) {
    	boolean editMode = false;
    	Fitxer fForm;
    	if(id!=null){
    		editMode = true;
    		fForm = fitxerService.findByPk(new FitxerKey(id, typeDocument));
    		// Busquem les claus
    		fForm.setClaus(DBUtils.searchClaus(fForm));
        	model.addAttribute("fitxerForm", fForm);
        	// Claus relacionades amb el tipus de document seleccionat
        	List<Clau> lK = clauService.findByType(fForm.getPk().getTypeDocument());
        	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
    	} else {
    		fForm = new Fitxer();
        	fForm.getPk().setTypeDocument(Constants.TYPE_KEY_IMAGE);
        	model.addAttribute("fitxerForm", fForm);
        	// Claus relacionades amb el tipus de document per defecte
        	List<Clau> lK = clauService.findByType(fForm.getPk().getTypeDocument());
        	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
    	}
    	model.addAttribute("autorList", autorService.findAll());
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuRegistreActive", "active");
    	model.addAttribute("editMode", editMode);        
        return "/arxiu/registre";
    }
        
    @RequestMapping(value = "/arxiu/create", method = RequestMethod.POST)
    public String create(@RequestParam("file") String file, @ModelAttribute("fitxerForm") Fitxer fitxerForm, BindingResult bindingResult, Model model) {
    	boolean newFile = true;
    	Fitxer olderForm = null;
    	if(fitxerForm.getPk().getId()!=null){
    		newFile = false;
    		// Obtenim el valor de l'antic fitxer
    		olderForm = fitxerService.findByPk(new FitxerKey(fitxerForm.getPk().getId(), fitxerForm.getPk().getTypeDocument()));
    	}
    	
    	// Validem el formulari
    	arxiuValidator.validate(fitxerForm, bindingResult);
    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	loadViewRegistre(model, fitxerForm, newFile, true);
        	return "/arxiu/registre";
        }
        
        // Només en les altes
        if(fitxerForm.getPk().getId()==null){
        	// Només per fitxers
        	if(!fitxerForm.getPk().getTypeDocument().equals(Constants.TYPE_KEY_DIGITAL)){
	        	if (file==null || "".equals(file)) {
		        	model.addAttribute("messageError", "Cal seleccionar un fitxer");
		        	loadViewRegistre(model, fitxerForm, newFile, false);
		        	return "/arxiu/registre";
		        } else {
		        	// Obtenim el format
		        	fitxerForm.setFormat(FilenameUtils.getExtension(file).toLowerCase());
		        	// Generem el nom del fitxer
		        	FileUtils.generateFileName(fitxerForm);
		        }
        	}
        } else {
        	// Generem el nom del fitxer
        	FileUtils.generateFileName(fitxerForm);
        }

    	// Convertim les paraules clau a Claus
    	ArrayList<Clau> hsC = new ArrayList<Clau>();
        for(String clau : fitxerForm.getParaulesClau().split(",")){
        	Clau c = clauService.findByNameAndType(clau.trim(), fitxerForm.getPk().getTypeDocument());
        	if(c==null){
            	model.addAttribute("messageError", "La clau '" + clau + "' no existeix");
            	loadViewRegistre(model, fitxerForm, newFile, false);
	        	return "/arxiu/registre";
        	}
        	hsC.add(c);
        }
        fitxerForm.setClaus(hsC);
        
    	// Guardem el fitxer
        if(newFile){
	        try {
	        	if(!fitxerForm.getPk().getTypeDocument().equals(Constants.TYPE_KEY_DIGITAL)){
	        		Path sourcepath = Paths.get(Constants.LOAD_FOLDER + file);
	        		Path destinationepath = Paths.get(Constants.UPLOADED_FOLDER + fitxerForm.getFileName() + "." + fitxerForm.getFormat());
	        		try {
	        			Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
	        		} catch(Exception e){
	        			e.printStackTrace();
	        			model.addAttribute("messageError", "El fitxer no s'ha pogut crear");
	                	loadViewRegistre(model, fitxerForm, newFile, false);
	    	        	return "/arxiu/registre";
	        		}
		            if(fitxerForm.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
			            // Creem el thumbnail
			            try { FileUtils.createThumbnails(fitxerForm); } catch(Exception e){}
		            }
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	            model.addAttribute("messageError", "El document " + fitxerForm.getFileName() + " no s'ha pogut crear al disc");
	            loadViewRegistre(model, fitxerForm, newFile, false);
	            return "/arxiu/registre";
	        }
        } else {
        	// Sí canviem la data, hem de canviar els fitxers
        	if(olderForm.getFileName().compareTo(fitxerForm.getFileName())!=0){
	        	File oldfile = new File(Constants.UPLOADED_FOLDER + olderForm.getFileName() + "." + olderForm.getFormat());
	            File newfile = new File(Constants.UPLOADED_FOLDER + fitxerForm.getFileName() + "." + fitxerForm.getFormat());
	            // Movem el fitxer
	            oldfile.renameTo(newfile);
	            
	            File oldfileThumbnail = new File(Constants.UPLOADED_FOLDER_THUMBNAILS + olderForm.getFileName() + "." + olderForm.getFormat());
	            File newfileThumbnail = new File(Constants.UPLOADED_FOLDER_THUMBNAILS + fitxerForm.getFileName() + "." + fitxerForm.getFormat());
	            // Movem el fitxer
	            oldfileThumbnail.renameTo(newfileThumbnail);
        	}
        }

    	// Registrem a la BBDD el fitxer
        try {
        	fitxerService.save(fitxerForm);
        } catch(Exception e){
        	e.printStackTrace();
        	model.addAttribute("messageError", "El document " + fitxerForm.getFileName() + " no s'ha pogut crear");
            loadViewRegistre(model, fitxerForm, newFile, false);
            return "/arxiu/registre";
        }
        
        // Si arribem a aquest punt, tot ha anat correctament
        model.addAttribute("messageOk", "Document registrat correctament " + fitxerForm.getFileName());
        // Parametres per poder carregar la vista
        loadViewRegistre(model, fitxerForm, newFile, false);
        return "/arxiu/registre";
    }

    @RequestMapping(value = "/arxiu/updateFile", method = RequestMethod.POST)
    public String updateFile(@RequestParam("file") String file, @ModelAttribute("fitxerForm") Fitxer fitxerForm, BindingResult bindingResult, Model model) {
    	// Actualitzar el fitexr
    	try {
    		Path sourcepath = Paths.get(Constants.LOAD_FOLDER + file);
    		Path destinationepath = Paths.get(Constants.UPLOADED_FOLDER + fitxerForm.getFileName() + "." + fitxerForm.getFormat());
    		Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
            if(fitxerForm.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
	            // Creem el thumbnail
	            try { FileUtils.createThumbnails(fitxerForm); } catch(Exception e){}
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("messageError", "El document " + fitxerForm.getFileName() + " no s'ha pogut crear al disc");
            loadViewRegistre(model, fitxerForm, false, false);
            return "/arxiu/registre";
        }

        // Si arribem a aquest punt, tot ha anat correctament
        model.addAttribute("messageOk", "Fitxer actualitzat correctament");
        // Parametres per poder carregar la vista
        loadViewRegistre(model, fitxerForm, false, false);
        return "/arxiu/registre";
    }
    
    @RequestMapping(value = "/arxiu/downloadImage", produces="application/zip")
    public void downloadImage(Long id, Long typeDocument, HttpServletResponse response) {
    	try {
    		Fitxer fForm = null;;
	    	if(id!=null){
		    	fForm = fitxerService.findByPk(new FitxerKey(id, typeDocument));
		    	// Busquem les claus
	    		fForm.setClaus(DBUtils.searchClaus(fForm));
		    	// Busquem el llistat d'autors
		    	List<Autor> autorList = autorService.findAll();
		    	for(Autor a : autorList){
		    		if(fForm.getAutor_id().equals(a.getId())){
		    			// Obtenim el nom de l'autor
		    			fForm.setAutor(a.getName());
		    			break;
		    		}
		    	}
	    	} else {
	    		// Error
	    	}
	    	
	    	// Setting headers
	        response.setContentType("application/zip");
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.addHeader("Content-Disposition", "attachment; filename=\"" + fForm.getFileName() + ".zip\"");
	
	        // Creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
	        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
	
	        // Simple file list, just for tests
	        File file = new File(Constants.UPLOADED_FOLDER + fForm.getFileName() + "." + fForm.getFormat());
	
	        // Packing Image
	        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
	        FileInputStream fileInputStream = new FileInputStream(file);
	        IOUtils.copy(fileInputStream, zipOutputStream);
	        fileInputStream.close();
	        try { zipOutputStream.closeEntry(); } catch(Exception e){}
	        
	        // Packing PDF
	        zipOutputStream.putNextEntry(new ZipEntry("Fitxa " + fForm.getFileName() + ".pdf"));
	        PDFUtils.pdfFitxaFitxer(zipOutputStream, fForm);
	        try { zipOutputStream.closeEntry(); } catch(Exception e){}
	
	        if (zipOutputStream != null) {
	        	try { 
		        	zipOutputStream.finish();
		            zipOutputStream.flush();
		            IOUtils.closeQuietly(zipOutputStream);
	        	} catch(Exception e){}
	        }
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void loadViewRegistre(Model model, Fitxer fitxerForm, boolean newFile, boolean validatorError){
    	model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarArxiuRegistreActive", "active");
        // Reiniciem el fitxer
        if(newFile){
        	fitxerForm = new Fitxer();
        	fitxerForm.getPk().setTypeDocument(Constants.TYPE_KEY_IMAGE);
        }
        // Claus relacionades amb el tipus de document seleccionat
    	List<Clau> lK = clauService.findByType(fitxerForm.getPk().getTypeDocument());
    	model.addAttribute("paraulesClauList", ListUtils.listClausToString(lK));
        if(newFile){
        	model.addAttribute("editMode", false);
        } else {
        	model.addAttribute("editMode", true);
        }
    	model.addAttribute("autorList", autorService.findAll());
    	if(!validatorError){
    		model.addAttribute("fitxerForm", fitxerForm);
    	}
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/arxiu/eliminar", method = RequestMethod.GET)
    public String eliminar(Model model, Long id, Long typeDocumentPk, Boolean searchOn, String referencia, String referenciaArxiu, String titol, Long year, Long typeDocument, String paraulesClau, Long autor) {
    	// Obtenim el fitxer
    	Fitxer f = fitxerService.findByPk(new FitxerKey(id, typeDocumentPk));
    	
    	// Eliminem el fitxer fisic del disc
    	try {
	    	File file = new File(Constants.UPLOADED_FOLDER + f.getFileName() + "." + f.getFormat());
	    	if(file!=null){
				if(file.delete()){
					System.out.println(file.getName() + " esborrat");
				} else {
					System.out.println("Error esborrant el fitxer");
				}
	    	}
    	} catch(Exception e){
    		System.out.println("Error esborrant el fitxer");
    		e.printStackTrace();
    	}
    	
    	// Eliminem la miniatura
    	try {
	    	File fileThumbnails = new File(Constants.UPLOADED_FOLDER_THUMBNAILS + f.getFileName() + "." + f.getFormat());
	    	if(fileThumbnails!=null){
				if(fileThumbnails.delete()){
					System.out.println(fileThumbnails.getName() + " esborrat");
				} else {
					System.out.println("Error esborrant el fitxer");
				}
	    	}
    	} catch(Exception e){
    		System.out.println("Error esborrant el fitxer");
    		e.printStackTrace();
    	}
    	
    	// Eliminem el fitxer
    	fitxerService.delete(f);
    	
    	// Cerquem els resultats
    	if(searchOn!=null && searchOn){
    		SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
            criteria.setReferencia(referencia);
            criteria.setReferenciaArxiu(referenciaArxiu);
        	criteria.setTitol(titol);
        	criteria.setYear(year);
        	criteria.setTypeDocument(typeDocument);
        	criteria.setParaulesClau(paraulesClau);
        	criteria.setAutor_id(autor);
        	if(autor!=null){
        		Autor a = autorService.findById(autor);
            	criteria.setAutor(a.getName());
        	}
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
    			listFitxers = (List<Fitxer>)DBUtils.searchForView(criteria).getResult();
    		} catch(Exception e){ 
    			System.out.println("S'ha produit un error recuperant les dades");
    		}
    		model.addAttribute("searchOn", searchOn);
    		model.addAttribute("fileName", referencia);
    		model.addAttribute("referencia", referenciaArxiu);
    		model.addAttribute("titol", titol);
    		model.addAttribute("year", year);
    		model.addAttribute("typeDocument", typeDocument);
    		model.addAttribute("autor_id", autor);
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
        model.addAttribute("autorList", autorService.findAll());
        
    	return "/arxiu/consulta";
    }
}