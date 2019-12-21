package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Fitxer;
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
        Fitxer fitxer = (Fitxer) o;

        if (fitxer.getTitol()==null || "".equals(fitxer.getTitol())){
        	errors.rejectValue("titol", "required");
        } else if (fitxer.getTitol().length() < 4 || fitxer.getTitol().length() > 100) {
            errors.rejectValue("titol", "size.fitxerForm.titol");
        }
        if(fitxer.getPk().getTypeDocument()==null || fitxer.getPk().getTypeDocument()<1 || fitxer.getPk().getTypeDocument()>2){
        	errors.rejectValue("typeDocument", "required");
        }
        if(fitxer.getParaulesClau()==null || "".equals(fitxer.getParaulesClau())){
        	errors.rejectValue("paraulesClau", "required");
        }
    }
}