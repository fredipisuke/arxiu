package cat.reisigualada.reis.web.arxiu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.model.Autor;
import cat.reisigualada.reis.service.AutorService;
import cat.reisigualada.reis.validator.AutorValidator;

@Controller
public class AutorController {
    @Autowired
    private AutorService autorService;
    @Autowired
    private AutorValidator autorValidator;

    @RequestMapping(value = "/autors/consulta", method = RequestMethod.GET)
    public String consulta(@ModelAttribute("autorForm") Autor autorForm, Model model) {
    	model.addAttribute("autors", autorService.findAll());
        model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarAutorsActive", "active");
        
        return "/autors/consulta";
    }
    
    @RequestMapping(value = "/autors/registrationAutor", method = RequestMethod.GET)
    public String registrationAutor(Model model, Long id) {
    	boolean editMode = false;
    	if(id!=null){
    		model.addAttribute("autorForm", autorService.findById(id));
    		editMode = true;
    	} else {
    		model.addAttribute("autorForm", new Autor());
    	}
        model.addAttribute("NavBarArxiuActive", "active");
        model.addAttribute("NavBarAutorsActive", "active");
        model.addAttribute("editMode", editMode);
    	
        return "/autors/registrationAutor";
    }

    @RequestMapping(value = "/autors/registrationAutor", method = RequestMethod.POST)
    public String registrationAutor(@ModelAttribute("autorForm") Autor autorForm, BindingResult bindingResult, Model model) {
        // Validem el formulari
    	autorValidator.validate(autorForm, bindingResult);

    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	if(autorForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/autors/registrationAutor";
        }

        // Creem el perfil
        autorService.save(autorForm);

    	// Carreguem els filtres de cerca
    	model.addAttribute("autorForm", new Autor());
    	
        return "redirect:/autors/consulta";
    }
    
    @RequestMapping(value = "/autors/deleteAutors", method = RequestMethod.GET)
    public String deleteAutors(Model model, Long id) {
        // Eliminem el perfil
    	autorService.deleteById(id);
    	
    	// Carreguem els filtres de cerca
    	model.addAttribute("autorForm", new Autor());
        
    	return "redirect:/autors/consulta";
    }
}