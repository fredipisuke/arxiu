package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.service.ClauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClauValidator implements Validator {
    @Autowired
    private ClauService clauService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Clau.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Clau clau = (Clau) o;

        if (clau.getName()==null || "".equals(clau.getName())){
        	errors.rejectValue("name", "required");
        } else if (clau.getName().length() < 0 || clau.getName().length() > 32) {
            errors.rejectValue("name", "size.clauForm.name");
        } else if (clauService.findByNameAndType(clau.getName(), clau.getType()) != null) {
            errors.rejectValue("name", "duplicate.clauForm.name");
        }
    }
}