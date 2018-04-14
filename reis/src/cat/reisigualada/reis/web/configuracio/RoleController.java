package cat.reisigualada.reis.web.configuracio;

import cat.reisigualada.reis.model.Role;
import cat.reisigualada.reis.service.RoleService;
import cat.reisigualada.reis.validator.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleValidator roleValidator;

    @RequestMapping(value = "/configuration/config_roles", method = RequestMethod.GET)
    public String configurationRoles(Model model) {
    	model.addAttribute("roles", roleService.findAll());
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarRolesActive", "active");

        return "/configuration/config_roles";
    }
    
    @RequestMapping(value = "/configuration/registrationRole", method = RequestMethod.GET)
    public String registrationRole(Model model, Long id) {
    	boolean editMode = false;
    	if(id!=null){
    		model.addAttribute("roleForm", roleService.findById(id));
    		editMode = true;
    	} else {
    		model.addAttribute("roleForm", new Role());
    	}
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarRolesActive", "active");
        model.addAttribute("editMode", editMode);
    	
        return "/configuration/registrationRole";
    }

    @RequestMapping(value = "/configuration/registrationRole", method = RequestMethod.POST)
    public String registrationRole(@ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult, Model model) {
        // Validem el formulari
    	roleValidator.validate(roleForm, bindingResult);

    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	if(roleForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/configuration/registrationRole";
        }

        // Creem el perfil
        roleService.save(roleForm);

        return "redirect:/configuration/config_roles";
    }
    
    @RequestMapping(value = "/configuration/deleteRole", method = RequestMethod.GET)
    public String deleteRole(Model model, Long id) {
        // Eliminem el perfil
    	roleService.deleteById(id);
        return "redirect:/configuration/config_roles";
    }
}
