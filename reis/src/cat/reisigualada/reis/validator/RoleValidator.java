package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Role;
import cat.reisigualada.reis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Role role = (Role) o;

        if (role.getName()==null || "".equals(role.getName())){
        	errors.rejectValue("name", "required");
        } else if (role.getName().length() < 4 || role.getName().length() > 32) {
            errors.rejectValue("name", "size.roleForm.name");
        } else if (roleService.findByName(role.getName()) != null) {
            errors.rejectValue("name", "duplicate.roleForm.name");
        }
    }
}