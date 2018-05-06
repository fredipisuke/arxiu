package cat.reisigualada.reis.model.lists;

import java.util.List;
import javax.servlet.ServletOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.utils.Constants;

public class PDFList {
	
	public static void pdfClaus(ServletOutputStream out, List<Clau> listClaus) throws Exception {
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
			} else if(key.getType().equals(Constants.TYPE_KEY_DOCUMENTS)){
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
		Document pdf = new Document(PageSize.A4.rotate(), 5, 5, 36, 36);
		PdfWriter.getInstance(pdf, out);
		pdf.open();
		PdfPTable pdfTaula = new PdfPTable(8);
		PdfPCell tableCell;
		
		Phrase pT = new Phrase("ID", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("TITOL", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("TIPUS DOCUMENT", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("PARAULES CLAU", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("AUTOR", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("UBICACIÓ FOTOGRAFIA / UBICACIÓ ARXIU", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("PROCEDÈNCIA", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
		tableCell = new PdfPCell(pT);
		createTitleCell(tableCell);
		pdfTaula.addCell(tableCell);
		pT = new Phrase("OBSERVACIONS", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
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
			} else if(f.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS)){
				pD = new Phrase("Document", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else {
				pD = new Phrase(f.getTypeDocument().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			}
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
			
			pD = new Phrase(f.getParaulesClau().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getAutor().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			if(f.getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
				pD = new Phrase(f.getUbicacio().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else if(f.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS)){
				pD = new Phrase(f.getUbicacioArxiu().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			}
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getProcedencia().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getObservacions().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
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
