package cat.reisigualada.reis.web.nens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.model.Escola;
import cat.reisigualada.reis.service.EscolaService;
import cat.reisigualada.reis.validator.EscolaValidator;

@Controller
public class EscolaController {
    @Autowired
    private EscolaService escolaService;
    @Autowired
    private EscolaValidator escolaValidator;

    @RequestMapping(value = "/escoles/consulta", method = RequestMethod.GET)
    public String consulta(@ModelAttribute("escolaForm") Escola escolaForm, Model model) {
    	model.addAttribute("escoles", escolaService.findAll());
        model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarEscolesActive", "active");
        
        return "/escoles/consulta";
    }
    
    @RequestMapping(value = "/escoles/registrationEscola", method = RequestMethod.GET)
    public String registrationEscola(Model model, Long id) {
    	boolean editMode = false;
    	if(id!=null){
    		model.addAttribute("escolaForm", escolaService.findById(id));
    		editMode = true;
    	} else {
    		model.addAttribute("escolaForm", new Escola());
    	}
        model.addAttribute("NavBarNensActive", "active");
        model.addAttribute("NavBarEscolesActive", "active");
        model.addAttribute("editMode", editMode);
    	
        return "/escoles/registrationEscola";
    }

    @RequestMapping(value = "/escoles/registrationEscola", method = RequestMethod.POST)
    public String registrationEscola(@ModelAttribute("escolaForm") Escola escolaForm, BindingResult bindingResult, Model model) {
        // Validem el formulari
    	escolaValidator.validate(escolaForm, bindingResult);

    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	if(escolaForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/escoles/registrationEscola";
        }

        // Creem el perfil
        escolaService.save(escolaForm);

    	// Carreguem els filtres de cerca
    	model.addAttribute("escolaForm", new Escola());
    	
        return "redirect:/escoles/consulta";
    }
    
    @RequestMapping(value = "/escoles/deleteEscola", method = RequestMethod.GET)
    public String deleteEscola(Model model, Long id) {
        // Eliminem el perfil
    	escolaService.deleteById(id);
    	
    	// Carreguem els filtres de cerca
    	model.addAttribute("escolaForm", new Escola());
        
    	return "redirect:/escoles/consulta";
    }
}