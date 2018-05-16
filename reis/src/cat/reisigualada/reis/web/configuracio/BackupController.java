package cat.reisigualada.reis.web.configuracio;

import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cat.reisigualada.reis.service.DBUtils;

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
}
