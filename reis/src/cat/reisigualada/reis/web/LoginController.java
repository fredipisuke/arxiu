package cat.reisigualada.reis.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    	
    	return "welcome";
    }
}
