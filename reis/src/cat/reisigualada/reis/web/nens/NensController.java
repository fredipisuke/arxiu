package cat.reisigualada.reis.web.nens;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cat.reisigualada.reis.model.Nen;
import cat.reisigualada.reis.model.documentacio.ExcelUtils;
import cat.reisigualada.reis.model.documentacio.PDFUtils;
import cat.reisigualada.reis.service.EscolaService;
import cat.reisigualada.reis.service.DBUtils;
import cat.reisigualada.reis.service.NenService;
import cat.reisigualada.reis.utils.AjaxResponseBody;
import cat.reisigualada.reis.validator.NensValidator;

@Controller
public class NensController {
    @Autowired
    private NenService nensService;
    @Autowired
    private EscolaService escolaService;
    @Autowired
    private NensValidator nensValidator;

    private static String SESSION_SEARCH = "SearchCriteriaNensSession";
    
    @RequestMapping(value = "/nens/consulta", method = RequestMethod.GET)
    public String consulta(Model model, HttpServletRequest request) {
    	model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarNensConsultaActive", "active");
        model.addAttribute("escolaList", escolaService.findAllByOrderByDescripcio());
        
        // Eliminamos los datos de la sessión
        request.getSession().removeAttribute(SESSION_SEARCH);
        
        return "/nens/consulta";
    }
    
    @RequestMapping(value = "/nens/consultaBack", method = RequestMethod.GET)
    public String consultaBack(Model model, HttpServletRequest request) {
    	model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarNensConsultaActive", "active");
        model.addAttribute("escolaList", escolaService.findAllByOrderByDescripcio());
        
        SearchCriteriaNens criteria = null;
        try{ criteria = (SearchCriteriaNens)request.getSession().getAttribute(SESSION_SEARCH); } catch(Exception e){}
        if(criteria!=null){
        	model.addAttribute("id", criteria.getId());
        	model.addAttribute("nom", criteria.getNom());
        	model.addAttribute("escola_id", criteria.getEscola_id());
        	model.addAttribute("sexe", criteria.getSexe());
        	model.addAttribute("edat", criteria.getEdat());
        	model.addAttribute("pagina", criteria.getPagina());
        	model.addAttribute("searchOn", "true");
        }
        
        return "/nens/consulta";
    }
    
    @RequestMapping(value = "/nens/consultaNens", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody AjaxResponseBody consultaNens(@RequestBody SearchCriteriaNens criteria, BindingResult bindingResult, Model model, HttpServletRequest request){
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
    
    @RequestMapping(value = "/nens/downloadExcel", method = RequestMethod.GET)
    public void downloadExcel(Model model, Long id, String nom, Long escola_id, String sexe, Long edat, HttpServletResponse response) throws Exception {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=llistat_cerca.xlsx");
        
        SearchCriteriaNens criteria = new SearchCriteriaNens();
        criteria.setId(id);
        criteria.setNom(nom);
    	criteria.setEscola_id(escola_id);
    	criteria.setSexe(sexe);
    	criteria.setEdat(edat);
    	
    	List<Nen> listNens = null;
		try{
			listNens = DBUtils.searchForDocument(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}
    	
    	ExcelUtils.excelNens(response.getOutputStream(), listNens);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/nens/downloadPDF", method = RequestMethod.GET)
    public void downloadPDF(Model model, Long id, String nom, Long escola_id, String sexe, Long edat, HttpServletResponse response) throws Exception {
    	response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=llistat_cerca.pdf");
        
        SearchCriteriaNens criteria = new SearchCriteriaNens();
        criteria.setId(id);
        criteria.setNom(nom);
    	criteria.setEscola_id(escola_id);
    	criteria.setSexe(sexe);
    	criteria.setEdat(edat);
    	
    	List<Nen> listNens = null;
		try{
			listNens = DBUtils.searchForDocument(criteria);
		} catch(Exception e){ 
			System.out.println("S'ha produit un error recuperant les dades");
		}    	

    	PDFUtils.pdfNens(response.getOutputStream(), criteria, listNens);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/nens/registre", method = RequestMethod.GET)
    public String registre(Model model, Long id) {
    	boolean editMode = false;
    	Nen nForm;
    	if(id!=null){
    		editMode = true;
    		nForm = nensService.findById(id);
        	model.addAttribute("nenForm", nForm);
    	} else {
    		nForm = new Nen();
        	model.addAttribute("nenForm", nForm);
    	}
    	model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarNensRegistreActive", "active");
        model.addAttribute("escolaList", escolaService.findAllByOrderByDescripcio());
    	model.addAttribute("editMode", editMode);        
        return "/nens/registre";
    }
        
    @RequestMapping(value = "/nens/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("nenForm") Nen nenForm, BindingResult bindingResult, Model model) {
    	boolean newFile = true;
    	Nen olderForm = null;
    	if(nenForm.getId()!=null){
    		newFile = false;
    		// Obtenim el valor de l'antic fitxer
    		olderForm = nensService.findById(nenForm.getId());
    	}
    	
    	// Validem el formulari
    	nensValidator.validate(nenForm, bindingResult);
    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	loadViewRegistre(model, nenForm, newFile, true);
        	return "/nens/registre";
        }
        
    	// Registrem a la BBDD el nen
        try {
        	nensService.save(nenForm);
        } catch(Exception e){
        	e.printStackTrace();
        	model.addAttribute("messageError", "El nen " + nenForm.getNom() + " no s'ha pogut crear");
            loadViewRegistre(model, nenForm, newFile, false);
            return "/nens/registre";
        }
        
        // Si arribem a aquest punt, tot ha anat correctament
        model.addAttribute("messageOk", "Nen registrat correctament " + nenForm.getNom());
        // Parametres per poder carregar la vista
        loadViewRegistre(model, nenForm, newFile, false);
        return "/nens/registre";
    }
    
    private void loadViewRegistre(Model model, Nen nenForm, boolean newFile, boolean validatorError){
    	model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarNensRegistreActive", "active");
        model.addAttribute("escolaList", escolaService.findAllByOrderByDescripcio());
        
        // Reiniciem el fitxer
        if(newFile){
        	nenForm = new Nen();
        	model.addAttribute("editMode", false);
        } else {
        	model.addAttribute("editMode", true);
        }
        
    	if(!validatorError){
    		model.addAttribute("nenForm", nenForm);
    	}
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/nens/eliminar", method = RequestMethod.GET)
    public String eliminar(Model model, Long id, String nom, Long escola_id, String sexe, Long edat, Boolean searchOn) {
    	// Obtenim el fitxer
    	Nen n = nensService.findById(id);
    	
    	// Cerquem els resultats
    	if(searchOn!=null && searchOn){
    		SearchCriteriaNens criteria = new SearchCriteriaNens();
    		criteria.setId(id);
            criteria.setNom(nom);
        	criteria.setEscola_id(escola_id);
        	criteria.setSexe(sexe);
        	criteria.setEdat(edat);
        	
        	List<Nen> listNens = null;
    		try{
    			listNens = (List<Nen>)DBUtils.searchForView(criteria).getResult();
    		} catch(Exception e){ 
    			System.out.println("S'ha produit un error recuperant les dades");
    		}
    		model.addAttribute("id", criteria.getId());
        	model.addAttribute("nom", criteria.getNom());
        	model.addAttribute("escola_id", criteria.getEscola_id());
        	model.addAttribute("sexe", criteria.getSexe());
        	model.addAttribute("edat", criteria.getEdat());
        	model.addAttribute("pagina", criteria.getPagina());
    		model.addAttribute("searchOn", searchOn);
    		model.addAttribute("listNens", listNens);
    	} else {
    		
    	}
    	model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarNensConsultaActive", "active");
        model.addAttribute("escolaList", escolaService.findAllByOrderByDescripcio());
        
    	return "/nens/consulta";
    }
}