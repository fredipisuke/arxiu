package cat.reisigualada.reis.model.documentacio;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.web.arxiu.SearchCriteriaFitxers;

public class PDFUtils {
	
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
	
	public static void pdfFitxers(ServletOutputStream out, SearchCriteriaFitxers criteria, List<Fitxer> listFitxers) throws Exception {
		Document pdf = new Document(PageSize.A4.rotate(), 5, 5, 36, 36);
		PdfWriter.getInstance(pdf, out);
		pdf.open();
		
		// Parametres de la cerca
		PdfPTable pdfSearch = new PdfPTable(2);
		pdfSearch.setWidths(new int[]{1, 3});
		PdfPCell searchCell;
		// Referencia
		if(criteria.getReferencia()!=null && !"".equals(criteria.getReferencia())){
			Phrase pT1 = new Phrase("Referència:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getReferencia(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getReferenciaArxiu()!=null && !"".equals(criteria.getReferenciaArxiu())){
			Phrase pT1 = new Phrase("Referència antic arxiu:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getReferenciaArxiu(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getTitol()!=null && !"".equals(criteria.getTitol())){
			Phrase pT1 = new Phrase("Títol document:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getTitol(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getYear()!=null){
			Phrase pT1 = new Phrase("Any:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getYear().toString(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getTypeDocument()!=null){
			Phrase pT1 = new Phrase("Tipologia:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			String tipusTipologia = "";
			if(criteria.getTypeDocument().equals(Constants.TYPE_KEY_IMAGE))
				tipusTipologia = "Imatge";
			else if(criteria.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS))
				tipusTipologia = "Document";
			Phrase pT2 = new Phrase(tipusTipologia, new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau())){
			Phrase pT1 = new Phrase("Paraules clau:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getParaulesClau(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		if(criteria.getAutor_id()!=null){
			Phrase pT1 = new Phrase("Autor:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
			searchCell = new PdfPCell(pT1);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
			Phrase pT2 = new Phrase(criteria.getAutor(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
			searchCell = new PdfPCell(pT2);
			searchCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			searchCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			searchCell.setBorderWidth(0);
			pdfSearch.addCell(searchCell);
		}
		pdfSearch.setSpacingAfter(10);
		pdf.add(pdfSearch);
		
		// Taula de resultats 
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
			Phrase pD = new Phrase(f.getPk().getId().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			pD = new Phrase(f.getTitol().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
			
			if(f.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
				pD = new Phrase("Imatge", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else if(f.getPk().getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS)){
				pD = new Phrase("Document", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else {
				pD = new Phrase(f.getPk().getTypeDocument().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			}
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);
			
			pD = new Phrase(f.getParaulesClau().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			String autor = "";
			if(f.getAutor()!=null)
				autor = f.getAutor();
			pD = new Phrase(autor, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			tableCell = new PdfPCell(pD);
			pdfTaula.addCell(tableCell);

			if(f.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
				pD = new Phrase(f.getUbicacio().toString(), new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
			} else if(f.getPk().getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS)){
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
	
	public static Document pdfFitxaFitxer(OutputStream out, Fitxer fitxer) throws Exception {
		Document pdf = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter pdfWriter = PdfWriter.getInstance(pdf, out);
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
		PdfPCell dataCell1 = new PdfPCell(new Phrase("TÍTOL: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataCell1.setBorderWidth(0);
		dataCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell1);
		PdfPCell dataCell2 = new PdfPCell(new Phrase(fitxer.getTitol(), new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataCell2.setBorderWidth(0);
		dataCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell2);
		// DATA
		PdfPCell dataCell3 = new PdfPCell(new Phrase("DATA: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataCell3.setBorderWidth(0);
		dataCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell3);
		SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = dateFormatNew.format(fitxer.getData());
		PdfPCell dataCell4 = new PdfPCell(new Phrase(formattedDate, new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataCell4.setBorderWidth(0);
		dataCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell4);
		// PARAULES CLAU
		PdfPCell dataCell5 = new PdfPCell(new Phrase("PARAULES CLAU: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataCell5.setBorderWidth(0);
		dataCell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell5);
		PdfPCell dataCell6 = new PdfPCell(new Phrase(fitxer.getParaulesClau(), new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataCell6.setBorderWidth(0);
		dataCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell6);
		// AUTOR
		PdfPCell dataCell7 = new PdfPCell(new Phrase("AUTOR: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataCell7.setBorderWidth(0);
		dataCell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell7);
		PdfPCell dataCell8 = new PdfPCell(new Phrase(fitxer.getAutor(), new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataCell8.setBorderWidth(0);
		dataCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell8);
		
		// UBICACIÓ
		String TIPUS_UBICACIO = "";
		if(fitxer.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE))
			TIPUS_UBICACIO = "UBICACIÓ: ";
		else if(fitxer.getPk().getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS))
			TIPUS_UBICACIO = "UBICACIÓ A L'ARXIU: ";
		PdfPCell dataCell9 = new PdfPCell(new Phrase(TIPUS_UBICACIO, new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataCell9.setBorderWidth(0);
		dataCell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableData.addCell(dataCell9);
		PdfPCell dataCell10 = new PdfPCell(new Phrase(fitxer.getUbicacio(), new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataCell10.setBorderWidth(0);
		dataCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableData.addCell(dataCell10);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableData);
		
		// TAULA D'OBSERVACIONS
		// OBSERVACIONS
		PdfPTable tableObs = new PdfPTable(1);
		PdfPCell dataObsCell1 = new PdfPCell();
		dataObsCell1.addElement(new Phrase("OBSERVACIONS: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		dataObsCell1.addElement(new Phrase(fitxer.getObservacions(), new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)));
		dataObsCell1.setBorderWidth(0);
		dataObsCell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
		tableObs.addCell(dataObsCell1);
		tableObs.setSpacingAfter(20);
		// AFEGIM LA TAULA AL DOCUMENT
		pdf.add(tableObs);
		
		if(fitxer.getPk().getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
			// IMATGE
			Image img = Image.getInstance(Constants.UPLOADED_FOLDER + fitxer.getFileName() + "." + fitxer.getFormat());
			img.scaleToFit(200, 200);
			PdfPTable tableImgs = new PdfPTable(1);
			PdfPCell imageCell = new PdfPCell(img);
			imageCell.setBorderWidth(0);
			imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableImgs.addCell(imageCell);
			// AFEGIM LA TAULA AL DOCUMENT
			pdf.add(tableImgs);
		}
		
		// Cerramos el documento
		pdf.close();
		// Retornamos el documento PDF
		return pdf;
	}	
	
	private static void createTitleCell(PdfPCell tableCell){
		tableCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tableCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		tableCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	}
	
	private static Image generateBarCode39(PdfContentByte pdfContentByte, String code){
		// PdfContentByte cb = pdfWriter.getDirectContent();
		Barcode39 barCode = new Barcode39();
		barCode.setCode(code);
		Image barCodeImage = barCode.createImageWithBarcode(pdfContentByte, null, null);
		return barCodeImage;
	}
	
	private static Image generateBarCode128(PdfContentByte pdfContentByte, String code){
		// PdfContentByte cb = pdfWriter.getDirectContent();
		Barcode128 barCode = new Barcode128();
		barCode.setCode(code);
		barCode.setCodeType(Barcode.CODE128);
        Image barCodeImage = barCode.createImageWithBarcode(pdfContentByte, null, null);
		return barCodeImage;
	}
	
	private static Image generateBarCodeEAN(PdfContentByte pdfContentByte, String code){
		// PdfContentByte cb = pdfWriter.getDirectContent();
		BarcodeEAN barCode = new BarcodeEAN();
		barCode.setCode(code);
		barCode.setCodeType(Barcode.EAN13);
		Image barCodeImage = barCode.createImageWithBarcode(pdfContentByte, null, null);
		return barCodeImage;
	}
	
	private static Image generateQR(PdfContentByte pdfContentByte, String code) throws BadElementException{
		// PdfContentByte cb = pdfWriter.getDirectContent();
		BarcodeQRCode codeQR = new BarcodeQRCode(code, 1000, 1000, null);
        Image codeQRImage = codeQR.getImage();
        codeQRImage.scaleAbsolute(100, 100);
		return codeQRImage;
	}
}
