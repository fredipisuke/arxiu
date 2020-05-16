package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Escola;
import cat.reisigualada.reis.service.EscolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EscolaValidator implements Validator {
    @Autowired
    private EscolaService escolaService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Escola.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Escola escola = (Escola) o;

        if (escola.getDescripcio()==null || "".equals(escola.getDescripcio())){
        	errors.rejectValue("descripcio", "required");
        } else if (escola.getDescripcio().length() < 0 || escola.getDescripcio().length() > 32) {
            errors.rejectValue("descripcio", "size.escolaForm.descripcio");
        }
        Escola escolaDB = escolaService.findByDescripcio(escola.getDescripcio());
        // Nova escola
        if(escola.getId()==null){
        	if(escolaDB!=null){
        		errors.rejectValue("descripcio", "duplicate.escolaForm.descripcio");	
        	}
        } else {
        	if(escolaDB!=null && escolaDB.getId().longValue()!=escola.getId().longValue()){
        		errors.rejectValue("descripcio", "duplicate.escolaForm.descripcio");
        	}
        }
    }
}