package cat.reisigualada.reis.model.lists;

import java.util.List;
import javax.servlet.ServletOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.utils.Constants;

public class ExcelList {
	
	@SuppressWarnings("resource")
	public static void excelClaus(ServletOutputStream out, List<Clau> listClaus) throws Exception {
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(" Claus ");

		// Create row object
		XSSFRow row;

		// Capçalera
		int rowid = 0;
		row = spreadsheet.createRow(rowid++);
		Cell cellTitle = row.createCell(0);
		cellTitle.setCellValue("ID");
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("TIPUS");
		cellTitle = row.createCell(2);
		cellTitle.setCellValue("CLAU");
		
		// Iterate over data and write to sheet
		for (Clau key : listClaus) {
			row = spreadsheet.createRow(rowid++);
			Cell cellValue = row.createCell(0);
			cellValue.setCellValue(key.getId());
			cellValue = row.createCell(1);
			if(key.getType().equals(Constants.TYPE_KEY_IMAGE)){
				cellValue.setCellValue("Imatge");
			} else if(key.getType().equals(Constants.TYPE_KEY_DOCUMENTE)){
				cellValue.setCellValue("Document");
			} else {
				cellValue.setCellValue(key.getType());
			}
			cellValue = row.createCell(2);
			cellValue.setCellValue(key.getName());
		}

		// Write the workbook in file system
		workbook.write(out);
		out.close();
		System.out.println("Writesheet.xlsx written successfully");
	}
	
	@SuppressWarnings("resource")
	public static void excelFitxers(ServletOutputStream out, List<Fitxer> listFitxers) throws Exception {
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(" Fitxers ");

		// Create row object
		XSSFRow row;

		// Capçalera
		int rowid = 0;
		row = spreadsheet.createRow(rowid++);
		Cell cellTitle = row.createCell(0);
		cellTitle.setCellValue("ID");
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("TITOL");
		cellTitle = row.createCell(2);
		cellTitle.setCellValue("TIPUS DOCUMENT");
		cellTitle = row.createCell(3);
		cellTitle.setCellValue("PARAULES CLAU");
		cellTitle = row.createCell(4);
		cellTitle.setCellValue("AUTOR");
		cellTitle = row.createCell(5);
		cellTitle.setCellValue("UBICACIÓ");
		cellTitle = row.createCell(6);
		cellTitle.setCellValue("PROCEDENCIA");
		cellTitle = row.createCell(7);
		cellTitle.setCellValue("OBSERVACIONS");
		
		// Iterate over data and write to sheet
		for (Fitxer f : listFitxers) {
			row = spreadsheet.createRow(rowid++);
			Cell cellValue = row.createCell(0);
			cellValue.setCellValue(f.getId());
			cellValue = row.createCell(1);
			cellValue.setCellValue(f.getTitol());
			cellValue = row.createCell(2);
			if(f.getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
				cellValue.setCellValue("Imatge");
			} else if(f.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTE)){
				cellValue.setCellValue("Document");
			} else {
				cellValue.setCellValue(f.getTypeDocument());
			}
			cellValue = row.createCell(3);
			cellValue.setCellValue(f.getParaulesClau());
			cellValue = row.createCell(4);
			cellValue.setCellValue(f.getAutor());
			cellValue = row.createCell(5);
			cellValue.setCellValue(f.getUbicacio());
			cellValue = row.createCell(6);
			cellValue.setCellValue(f.getProcedencia());
			cellValue = row.createCell(7);
			cellValue.setCellValue(f.getObservacions());
		}

		// Write the workbook in file system
		workbook.write(out);
		out.close();
		System.out.println("Writesheet.xlsx written successfully");
	}
}
