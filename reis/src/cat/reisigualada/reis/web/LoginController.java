package cat.reisigualada.reis.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.service.DBUtils;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.web.arxiu.SearchCriteriaFitxers;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "L'usuari o la contrasenya és invàlid/a.");

        if (logout != null)
            model.addAttribute("message", "Has sortit satisfactoriament.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model, HttpServletRequest req) {
        model.addAttribute("NavBarIniciActive", "active");
        
        // Carreguem 4 imatges random
        SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
        criteria.setTypeDocument(Constants.TYPE_KEY_IMAGE);
		try{
			model.addAttribute("listImages", DBUtils.searchForWellcome(criteria).getResult());
		} catch(Exception e){
			System.out.println("S'ha produit un error recuperant les dades");
		}
		
		// Carreguem les estadístiques
		try {
			model.addAttribute("estadistiques", DBUtils.getStatistics());
		} catch(Exception e){
			System.out.println("S'ha produit un error recuperant les dades");
		}
    	
    	return "welcome";
    }
}
