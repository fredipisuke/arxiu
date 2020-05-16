package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.model.Nen;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NensValidator implements Validator {
	
    @Override
    public boolean supports(Class<?> aClass) {
        return Fitxer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Nen nen = (Nen) o;

        if (nen.getNom()==null || "".equals(nen.getNom())){
        	errors.rejectValue("nom", "required");
        } else if (nen.getNom().length() > 150) {
            errors.rejectValue("nom", "size.nenForm.nom");
        }
    }
}