package cat.reisigualada.reis.web.configuracio;

import cat.reisigualada.reis.model.Printer;
import cat.reisigualada.reis.printer.PrintUtility;
import cat.reisigualada.reis.service.PrinterService;
import cat.reisigualada.reis.validator.PrinterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrinterController {
    @Autowired
    private PrinterService printerService;
    @Autowired
    private PrinterValidator printerValidator;
      
	@RequestMapping(value = "/configuration/config_printers", method = RequestMethod.GET)
    public String configurationPrinters(Model model) {
    	model.addAttribute("printers", printerService.findAll());
        model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarPrintersActive", "active");

        return "/configuration/config_printers";
    }
    
    @RequestMapping(value = "/configuration/registrationPrinter", method = RequestMethod.GET)
    public String registrationPrinter(Model model, Long id) {
    	if(id!=null){
    		model.addAttribute("printerForm", printerService.findById(id));
    		model.addAttribute("editMode", true);
    	} else {
    		model.addAttribute("printerForm", new Printer());
    		model.addAttribute("editMode", false);
    	}
    	model.addAttribute("NavBarConfigurationActive", "active");
        model.addAttribute("NavBarPrintersActive", "active");
    	
        return "/configuration/registrationPrinter";
    }
    
    @RequestMapping(value = "/configuration/registrationPrinter", method = RequestMethod.POST)
    public String registrationRole(@ModelAttribute("printerForm") Printer printerForm, BindingResult bindingResult, Model model) {
        // Validem el formulari
    	printerValidator.validate(printerForm, bindingResult);

    	// Comprovem els errors
        if (bindingResult.hasErrors()) {
        	if(printerForm.getId()!=null){
            	model.addAttribute("editMode", true);
            } else {
            	model.addAttribute("editMode", false);
            }
            return "/configuration/registrationPrinter";
        }

        // Creem el perfil
        printerService.save(printerForm);

        return "redirect:/configuration/config_printers";
    }

    @RequestMapping(value = "/configuration/deletePrinter", method = RequestMethod.GET)
    public String deletePrinter(Model model, Long id) {
        // Eliminem l'impresora
    	printerService.deleteById(id);
        return "redirect:/configuration/config_printers";
    }
	
    @RequestMapping(value = "/configuration/printTest", method = RequestMethod.GET)
    public String printTest(Model model, String printername, Long printerid, String redirect) {
    	try {
    		if(printername!=null){
    			PrintUtility.print(printername, PrintUtility.createTestPage(), PrintUtility.PORTRAIT, PrintUtility.LONG_EDGE);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(redirect!=null && !"".equals(redirect)){
    		return "redirect:" + redirect;
    	} else {
	    	if(printerid!=null){
	    		return "redirect:/configuration/registrationPrinter?id=" + printerid;
	    	} else {
	    		return "redirect:/configuration/config_printers";
	    	}
    	}
    	
    }
}
