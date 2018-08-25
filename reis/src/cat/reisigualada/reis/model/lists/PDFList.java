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
		pT = new Phrase("REFERÈNCIA ARXIU", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
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

			pD = new Phrase(f.getReferencia().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getObservacions().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
		}
		pdf.add(pdfTaula);
		pdf.close();
	}
	
	public static void pdfFitxaFitxer(ServletOutputStream out, Fitxer fitxer) throws Exception {
		Document pdf = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter.getInstance(pdf, out);
		pdf.open();

		// IMATGE CAPÇALERA
		Image imgHeader = Image.getInstance("http://localhost:8080/reis/resources/images/logo_pdf.png");
		imgHeader.scaleToFit(200, 200);
		// TAULA CAPÇALERA
		PdfPTable tableHeader = new PdfPTable(2);
		PdfPCell imageHeaderCell = new PdfPCell(imgHeader);
		imageHeaderCell.setBorderWidth(0);
		imageHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableHeader.addCell(imageHeaderCell);
	    Phrase phraseHeader = new Phrase("ARXIU FOTOGRÀFIC \n"
										+ "COMISSIÓ CAVALCADA \n" 
										+ "DELS REIS D’IGUALADA, F. P.", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
	    PdfPCell headerCell = new PdfPCell(phraseHeader);
	    headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    headerCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	    headerCell.setBorderWidth(0);
	    tableHeader.addCell(headerCell);
	    tableHeader.setSpacingAfter(50);
		pdf.add(tableHeader);
		
		// REFERÈNCIA
		PdfPTable tableREF = new PdfPTable(2);
		PdfPCell refCell1 = new PdfPCell(new Phrase("REFERÈNCIA: ", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
		refCell1.setBorderWidth(0);
		refCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableREF.addCell(refCell1);
		PdfPCell refCell2 = new PdfPCell(new Phrase(fitxer.getFileName(), new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL)));
		refCell2.setBorderWidth(0);
		refCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableREF.addCell(refCell2);
		tableREF.setSpacingAfter(20);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableREF);
		
		// TAULA DE DADES
		// TÍTOL
		PdfPTable tableData = new PdfPTable(2);
		PdfPCell dataCell1 = new PdfPCell(new Phrase("TÍTOL: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataCell1.setBorderWidth(0);
		dataCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell1);
		PdfPCell dataCell2 = new PdfPCell(new Phrase(fitxer.getTitol(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataCell2.setBorderWidth(0);
		dataCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell2);
		// DATA
		PdfPCell dataCell3 = new PdfPCell(new Phrase("DATA: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataCell3.setBorderWidth(0);
		dataCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell3);
		PdfPCell dataCell4 = new PdfPCell(new Phrase(fitxer.getData().toString(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataCell4.setBorderWidth(0);
		dataCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell4);
		// PARAULES CLAU
		PdfPCell dataCell5 = new PdfPCell(new Phrase("PARAULES CLAU: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataCell5.setBorderWidth(0);
		dataCell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell5);
		PdfPCell dataCell6 = new PdfPCell(new Phrase(fitxer.getParaulesClau(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataCell6.setBorderWidth(0);
		dataCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell6);
		// AUTOR
		PdfPCell dataCell7 = new PdfPCell(new Phrase("AUTOR: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataCell7.setBorderWidth(0);
		dataCell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell7);
		PdfPCell dataCell8 = new PdfPCell(new Phrase(fitxer.getAutor(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataCell8.setBorderWidth(0);
		dataCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell8);
		// UBICACIÓ
		PdfPCell dataCell9 = new PdfPCell(new Phrase("UBICACIÓ: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataCell9.setBorderWidth(0);
		dataCell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell9);
		PdfPCell dataCell10 = new PdfPCell(new Phrase(fitxer.getUbicacio(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataCell10.setBorderWidth(0);
		dataCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell10);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableData);
		
		// TAULA D'OBSERVACIONS
		// OBSERVACIONS
		PdfPTable tableObs = new PdfPTable(1);
		PdfPCell dataObsCell1 = new PdfPCell();
		dataObsCell1.addElement(new Phrase("OBSERVACIONS: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		dataObsCell1.addElement(new Phrase(fitxer.getObservacions(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
		dataObsCell1.setBorderWidth(0);
		dataObsCell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
		tableObs.addCell(dataObsCell1);
		tableObs.setSpacingAfter(20);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableObs);
		
		// IMATGE
		Image img = Image.getInstance(Constants.UPLOADED_FOLDER_THUMBNAILS + fitxer.getFileName() + "." + fitxer.getFormat());
		PdfPTable tableImgs = new PdfPTable(1);
		PdfPCell imageCell = new PdfPCell(img);
		imageCell.setBorderWidth(0);
		imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableImgs.addCell(imageCell);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableImgs);
		 
		pdf.close();	
	}	
	
	private static void createTitleCell(PdfPCell tableCell){
		tableCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tableCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		tableCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}
}
