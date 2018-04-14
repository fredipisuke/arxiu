package cat.reisigualada.reis.validator;

import cat.reisigualada.reis.model.Printer;
import cat.reisigualada.reis.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PrinterValidator implements Validator {
    @Autowired
    private PrinterService printerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Printer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Printer printer = (Printer) o;

        if (printer.getPrintername()==null || "".equals(printer.getPrintername())){
        	errors.rejectValue("printername", "required");
        } else if (printer.getPrintername().length() < 4 || printer.getPrintername().length() > 32) {
            errors.rejectValue("printername", "size.rprinterForm.name");
        } else if (printerService.findByPrintername(printer.getPrintername()) != null) {
            errors.rejectValue("printername", "duplicate.printerForm.name");
        }
    }
}