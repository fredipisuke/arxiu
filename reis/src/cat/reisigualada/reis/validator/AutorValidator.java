package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Autor;
import cat.reisigualada.reis.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AutorValidator implements Validator {
    @Autowired
    private AutorService autorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Autor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Autor autor = (Autor) o;

        if (autor.getName()==null || "".equals(autor.getName())){
        	errors.rejectValue("name", "required");
        } else if (autor.getName().length() < 0 || autor.getName().length() > 32) {
            errors.rejectValue("name", "size.autorForm.name");
        } else if (autorService.findByName(autor.getName()) != null) {
            errors.rejectValue("name", "duplicate.autorForm.name");
        }
    }
}