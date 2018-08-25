package cat.reisigualada.reis.web.configuracio;

import java.nio.charset.Charset;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.service.DBUtils;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.utils.FileUtils;
import cat.reisigualada.reis.web.arxiu.SearchCriteriaFitxers;

@Controller
public class BackupController {
    
	@RequestMapping(value = "/backup/dataBase", method = RequestMethod.GET)
    public void downloadExcel(Model model, HttpServletResponse response) throws Exception {
    	response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=backup.sql");
        try {
			String bu = DBUtils.generateBackUp();
			ServletOutputStream out = response.getOutputStream();
			out.write(bu.getBytes(Charset.forName("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
        response.flushBuffer();
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backup/thumbnails", method = RequestMethod.GET)
    public String thumbnails(Model model, HttpServletRequest request) {
        // Carreguem totes les imatges
        SearchCriteriaFitxers criteria = new SearchCriteriaFitxers();
        criteria.setTypeDocument(Constants.TYPE_KEY_IMAGE);
        // Totes les imatges
        criteria.setnElementsPerPage(null);
        List<Fitxer> listImages = null;
		try {
			listImages = DBUtils.searchForView(criteria).getResult();
		} catch(Exception e){
			System.out.println("S'ha produit un error recuperant les dades");
		}
		
		// Creem totes les imatges en miniatura "Thumbnails"
		for(Fitxer f : listImages){
			try {
				FileUtils.createThumbnails(f);
			} catch(Exception e){
				System.out.println("S'ha produit un error creant les imatges en miniatura");
			}
		}
    	
		// Tornem al inici
		model.addAttribute("NavBarIniciActive", "active");
        // Carreguem 4 imatges random
        SearchCriteriaFitxers criteriaWellcome = new SearchCriteriaFitxers();
        criteriaWellcome.setTypeDocument(Constants.TYPE_KEY_IMAGE);
		try{
			model.addAttribute("listImages", DBUtils.searchForWellcome(criteriaWellcome).getResult());
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
