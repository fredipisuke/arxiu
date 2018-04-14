package cat.reisigualada.reis.web.configuracio;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.model.Role;
import cat.reisigualada.reis.model.User;
import cat.reisigualada.reis.service.PrinterService;
import cat.reisigualada.reis.service.RoleService;
import cat.reisigualada.reis.service.UserService;
import cat.reisigualada.reis.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrinterService printerService;
    @Autowired
    private UserValidator userValidator;
    protected EntityManager manager;

    @RequestMapping(value = "/configuration/config_users", method = RequestMethod.GET)
    public String configurationUsers(Model model) {
		model.addAttribute("users", userService.findAll());
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarUsersActive", "active");
		
        return "/configuration/config_users";
    }
    
    @RequestMapping(value = "/configuration/registrationUser", method = RequestMethod.GET)
    public String registrationUser(Model model, Long id) {
    	boolean editMode = false;
    	if(id!=null){
    		User u = new User();
    		u = userService.findById(id);
    		u.setCryptPassword(u.getPassword());
    		List<Role> sRoles = roleService.findAll();
    		for(int i=0; i<sRoles.size(); i++){
    			for(Role r : u.getRoles()){
    				if(sRoles.get(i).getId().equals(r.getId())){
    					sRoles.get(i).setChecked("checked");
    				}
    			}
    		}
            model.addAttribute("roles", sRoles);
            model.addAttribute("printers", printerService.findAll());
    		model.addAttribute("userForm", u);
    		editMode = true;
    	} else {
    		model.addAttribute("userForm", new User());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("printers", printerService.findAll());
    	}
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarUsersActive", "active");
        model.addAttribute("editMode", editMode);
        
        return "/configuration/registrationUser";
    }

    @RequestMapping(value = "/configuration/registrationUser", method = RequestMethod.POST)
    public String registrationUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
    	// Validem el formulari
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Role> sRoles = roleService.findAll();
    		if(userForm.getSelectedRoles()!=null && userForm.getSelectedRoles().size()>0){            
    			for(int i=0; i<sRoles.size(); i++){
	    			for(Long rL : userForm.getSelectedRoles()){
	    				if(sRoles.get(i).getId().equals(rL)){
	    					sRoles.get(i).setChecked("checked");
	    				}
	    			}
	    		}
    		}
            model.addAttribute("roles", sRoles);
            model.addAttribute("printers", printerService.findAll());
            if(userForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/configuration/registrationUser";
        }

        // Passem els rols seleccionats
        if(userForm.getSelectedRoles()!=null){
        	HashSet<Role> hsR = new HashSet<Role>();
	        for(Long role : userForm.getSelectedRoles()){
	        	Role r = new Role();
	        	r.setId(role); 
	        	hsR.add(r);
	        }
	        userForm.setRoles(hsR);
        }
        
        // Creem/guardem l'usuari
        userService.save(userForm);

        return "redirect:/configuration/config_users";
    }
    
    @RequestMapping(value = "/configuration/registrationUserPrinter", method = RequestMethod.GET)
    public String registrationUserPrinter(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	// Obtenim l'usuari per nom
    	model.addAttribute("userForm", userService.findByUsername(auth.getName()));
        model.addAttribute("printers", printerService.findAll());
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarUserPrinterActive", "active");
        
        return "/configuration/registrationUserPrinter";
    }
    
    @RequestMapping(value = "/configuration/registrationUserPrinter", method = RequestMethod.POST)
    public String registrationUserPrinter(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	// Obtenim l'usuari per nom
    	User userDB = userService.findByUsername(auth.getName());
    	userDB.setCryptPassword(userDB.getPassword());
    	userDB.setPassword(null);
        userDB.getPrinter().setId(userForm.getPrinter().getId());
        // Creem/guardem l'usuari
        userService.save(userDB);
        
        model.addAttribute("userForm", userDB);
        model.addAttribute("printers", printerService.findAll());
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarUserPrinterActive", "active");
        
        return "/configuration/registrationUserPrinter";
    }
    
    @RequestMapping(value = "/configuration/deleteUser", method = RequestMethod.GET)
    public String deleteUser(Model model, Long id) {
        // Eliminem l'usuari
        userService.deleteById(id);
        return "redirect:/configuration/config_users";
    }
}
