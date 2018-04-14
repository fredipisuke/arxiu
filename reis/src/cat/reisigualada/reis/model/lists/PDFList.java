package cat.reisigualada.reis.model.lists;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.utils.Constants;

public class PDFList {
	
	public static void pdfClaus(ServletOutputStream out, List<Clau> listClaus) throws Exception {
		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1", new Object[] { "EMP ID", "EMP NAME", "DESIGNATION" });
		empinfo.put("2", new Object[] { "tp01", "Gopal", "Technical Manager" });

		Document pdf = new Document(PageSize.A4, 36, 36, 54, 36);
		PdfWriter.getInstance(pdf, out);
		pdf.open();
		PdfPTable pdfTaula = new PdfPTable(3);
		PdfPCell tableCell;
		
		Phrase pT = new Phrase("ID", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("TIPUS", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("CLAU", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		
		for (Clau key : listClaus) {
			Phrase pD = new Phrase(key.getId().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
			
			if(key.getType().equals(Constants.TYPE_KEY_IMAGE)){
				pD = new Phrase("Imatge", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else if(key.getType().equals(Constants.TYPE_KEY_DOCUMENTE)){
				pD = new Phrase("Document", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else {
				pD = new Phrase(key.getType().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			}
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(key.getName().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
		}
		pdf.add(pdfTaula);
		pdf.close();
	}
	
	public static void pdfFitxers(ServletOutputStream out, List<Fitxer> listFitxers) throws Exception {
		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1", new Object[] { "EMP ID", "EMP NAME", "DESIGNATION" });
		empinfo.put("2", new Object[] { "tp01", "Gopal", "Technical Manager" });

		Document pdf = new Document(PageSize.A4, 36, 36, 54, 36);
		PdfWriter.getInstance(pdf, out);
		pdf.open();
		PdfPTable pdfTaula = new PdfPTable(3);
		PdfPCell tableCell;
		
		Phrase pT = new Phrase("ID", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("TIPUS", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("TIPUS DOCUMENT", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		
		for (Fitxer f : listFitxers) {
			Phrase pD = new Phrase(f.getId().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getTitol().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
			
			if(f.getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
				pD = new Phrase("Imatge", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else if(f.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTE)){
				pD = new Phrase("Document", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else {
				pD = new Phrase(f.getTypeDocument().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			}
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
		}
		pdf.add(pdfTaula);
		pdf.close();
	}
	
	private static void createTitleCell(PdfPCell tableCell){
		tableCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tableCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		tableCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}
}
