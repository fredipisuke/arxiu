package cat.reisigualada.reis.web.arxiu;

import java.util.List;
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
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.lists.ExcelList;
import cat.reisigualada.reis.model.lists.PDFList;
import cat.reisigualada.reis.service.ClauService;
import cat.reisigualada.reis.utils.AjaxResponseBody;
import cat.reisigualada.reis.validator.ClauValidator;

@Controller
public class ClausController {
    @Autowired
    private ClauService clauService;
    @Autowired
    private ClauValidator clauValidator;

    @RequestMapping(value = "/claus/consulta", method = RequestMethod.GET)
    public String consulta(@ModelAttribute("clauForm") Clau clauForm, Model model) {
    	model.addAttribute("claus", clauService.findAll());
        model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarClausActive", "active");
        
        return "/claus/consulta";
    }
    
    @RequestMapping(value = "/claus/getClaus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody AjaxResponseBody getClaus(@RequestBody SearchCriteriaClaus criteria, @ModelAttribute("clauForm") Clau clauForm, BindingResult bindingResult, Model model) {
		AjaxResponseBody result = new AjaxResponseBody();
		try{
			result.setResult(clauService.findByType(criteria.getTypeDocument()));	
		} catch(Exception e){
			result.setMsg("S'ha produit un error recuperant les dades");
		}
		return result;
    }
    
    @RequestMapping(value = "/claus/downloadExcel", method = RequestMethod.GET)
    public void downloadExcel(HttpServletResponse response) throws Exception {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=claus.xlsx");

        List<Clau> lK = clauService.findAllByOrderByTypeAscNameAsc();        
    	ExcelList.excelClaus(response.getOutputStream(), lK);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/claus/downloadPDF", method = RequestMethod.GET)
    public void downloadPDF(HttpServletResponse response) throws Exception {
    	response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=claus.pdf");

        List<Clau> lK = clauService.findAllByOrderByTypeAscNameAsc();
    	PDFList.pdfClaus(response.getOutputStream(), lK);
        response.flushBuffer();
    }
    
    @RequestMapping(value = "/claus/registrationClau", method = RequestMethod.GET)
    public String registrationClau(Model model, Long id) {
    	boolean editMode = false;
    	if(id!=null){
    		model.addAttribute("clauForm", clauService.findById(id));
    		editMode = true;
    	} else {
    		model.addAttribute("clauForm", new Clau());
    	}
        model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarClausActive", "active");
        model.addAttribute("editMode", editMode);
    	
        return "/claus/registrationClau";
    }

    @RequestMapping(value = "/claus/registrationClau", method = RequestMethod.POST)
    public String registrationClau(@ModelAttribute("clauForm") Clau clauForm, BindingResult bindingResult, Model model) {
        // Validem el formulari
    	clauValidator.validate(clauForm, bindingResult);

    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	if(clauForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/claus/registrationClau";
        }

        // Creem el perfil
        clauService.save(clauForm);

    	// Carreguem els filtres de cerca
    	model.addAttribute("clauForm", new Clau());
    	
        return "redirect:/claus/consulta";
    }
    
    @RequestMapping(value = "/claus/deleteClaus", method = RequestMethod.GET)
    public String deleteClaus(Model model, Long id) {
        // Eliminem el perfil
    	clauService.deleteById(id);
    	
    	// Carreguem els filtres de cerca
    	model.addAttribute("clauForm", new Clau());
        
    	return "redirect:/claus/consulta";
    }
}