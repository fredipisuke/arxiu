package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.User;
import cat.reisigualada.reis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        // Validem el número d'usuari
        if (user.getUsername()==null || "".equals(user.getUsername())){
        	errors.rejectValue("username", "required");
        } else if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "size.userForm.username");
        }
        // Validem el nom de l'uauris, per evitar repeticions
        if(user.getId()!=null){
        	User xUser = userService.findByUsername(user.getUsername());
        	if(xUser!=null && !xUser.getId().equals(user.getId())){
        		errors.rejectValue("username", "duplicate.userForm.username");
        	}
        } else {
	        if (userService.findByUsername(user.getUsername()) != null) {
	            errors.rejectValue("username", "duplicate.userForm.username");
	        }
        }
        
        // Validem la contrasenya
        if(user.getId()==null){
	        if (user.getPassword()==null || "".equals(user.getPassword())){
	        	errors.rejectValue("password", "required");
	        } else if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
	            errors.rejectValue("password", "size.userForm.password");
	        } else if (!user.getPasswordConfirm().equals(user.getPassword())) {
	            errors.rejectValue("passwordConfirm", "diff.userForm.passwordConfirm");
	        }
        }
        
        // Validem els perfils
        if (user.getSelectedRoles()==null || user.getSelectedRoles().size()==0){
        	errors.rejectValue("selectedRoles", "size.userForm.roles");
        } else {
        	boolean found = false;
        	for(Long r : user.getSelectedRoles()){
        		if(r==1) found=true;
        	}
        	if(!found){
        		errors.rejectValue("selectedRoles", "required.userForm.roleUser");
        	}
        }
        
        // Validem la impresora
        if(user.getPrinter()==null || user.getPrinter().getId()==null){
        	errors.rejectValue("printer", "required.userForm.printer");
        }
        
    }
}