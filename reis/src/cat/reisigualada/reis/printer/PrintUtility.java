package cat.reisigualada.reis.printer;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;

public class PrintUtility {

	public static String PORTRAIT 			= "PORTRAIT";
	public static String LANDSCAPE 			= "LANDSCAPE";
	public static String REVERSE_LANDSCAPE 	= "REVERSE_LANDSCAPE";
	
	public static String LONG_EDGE 			= "LONG_EDGE";
	public static String SHORT_EDGE 		= "SHORT_EDGE";
	
	public static void print(String printName, List<PagePrintable> listPages, String orientation, String duplexMode) throws PrintException, FileNotFoundException, IOException{ 
		try {
			int pageOrientation = 0;
            PrintRequestAttributeSet atr = new HashPrintRequestAttributeSet();
            // CONFIGURACIÓ DE LA PÀGINA
            if (LANDSCAPE.equals(orientation)) {
                atr.add(OrientationRequested.LANDSCAPE);
                pageOrientation = PageFormat.LANDSCAPE;
            } else if (REVERSE_LANDSCAPE.equals(orientation)) {
                atr.add(OrientationRequested.REVERSE_LANDSCAPE);
                pageOrientation = PageFormat.REVERSE_LANDSCAPE;
            } else {
                atr.add(OrientationRequested.PORTRAIT);
                pageOrientation = PageFormat.PORTRAIT;
            }
            if ("LONG_EDGE".equals(duplexMode)) {
                atr.add(Sides.TWO_SIDED_LONG_EDGE);
            } else {
                atr.add(Sides.TWO_SIDED_SHORT_EDGE);
            }
            
            // Obtenir totes les impresores instal·lades
        	PrintService[] printer = PrintServiceLookup.lookupPrintServices(null, null);      
        	// Indiquem amb quina impresora volem imprimir       
        	AttributeSet aset = new HashAttributeSet();
        	aset.add(new PrinterName(printName, null));
        	printer = PrintServiceLookup.lookupPrintServices(null, aset);
        	// Creem la tasca d'impressió
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setPrintService(printer[0]);

            // Creem el document
            Book book = new Book();
            PageFormat pageFormat = printJob.defaultPage();
            pageFormat.setOrientation(pageOrientation);
            for(Printable pL : listPages){
            	book.append(pL, pageFormat);
            }

            // Indiquem que es tracta de pàgines
            printJob.setPageable(book);

            // Imprimim
            try {
                printJob.print(atr);
            } catch (Exception PrintException) {
                PrintException.printStackTrace();
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
	}
	
	public static List<PagePrintable> createTestPage(){
    	List<PagePrintable> listPP = new ArrayList<PagePrintable>();
    	int posX_init = 9;
    	int posY_init = 20;    			
    	int posX = posX_init;
    	int posY = posY_init;
		List<PrintableLine> listPL = new ArrayList<PrintableLine>();
		Font titleFont = new Font("helvetica", Font.BOLD, 26);
		int fontMetrics = titleFont.getSize();
		for(String line : TITLE.split("\n")){
			while (line.length()>80){
    			String text = line.substring(0, 80);
    			line = line.substring(80);
    			PrintableLine pl = new PrintableLine(posX, posY, text, titleFont, Color.BLUE);
    			listPL.add(pl);
    			posY = posY + fontMetrics;
    		}
    		PrintableLine pl = new PrintableLine(posX, posY, line, titleFont, Color.BLUE);
			listPL.add(pl);
			posY = posY + fontMetrics;
		}
		// Text normal
		fontMetrics = 16;
    	for(String line : ETIQUETA.split("\n")){
    		while (line.length()>80){
    			String text = line.substring(0, 80);
    			line = line.substring(80);
    			PrintableLine pl = new PrintableLine(posX, posY, text, null, null);
    			listPL.add(pl);
    			posY = posY + fontMetrics;
    		}
    		PrintableLine pl = new PrintableLine(posX, posY, line, null, null);
			listPL.add(pl);
			posY = posY + fontMetrics;
    	}

    	// Construim les pagines
		List<PrintableLine> lPL = new ArrayList<PrintableLine>();
    	int j=0;
		for(int i=0; i<listPL.size(); i++){
			lPL.add(listPL.get(i));
    		if(j == 44){
    			PagePrintable pp = new PagePrintable(lPL);
    			listPP.add(pp);
    			lPL = new ArrayList<PrintableLine>();
    			j=0;
    		}
    		j++;
    	}
    	// Ultima pàgina
    	PagePrintable pp = new PagePrintable(lPL);
		listPP.add(pp);
    	
    	return listPP;
    }
	private static String TITLE = "PÀGINA DE PROVA";
	private static String ETIQUETA 
			= "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean "
			+ "commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis "
			+ "parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, "
			+ "pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, "
			+ "fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet "
			+ "a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer "
			+ "tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend "
			+ "tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam "
			+ "lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut "
			+ "metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. "
			+ "Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, "
			+ "tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque "
			+ "sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas "
			+ "nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. "
			+ "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed "
			+ "fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget "
			+ "bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce "
			+ "vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy "
			+ "id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit "
			+ "fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere "
			+ "cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis "
			+ "arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam "
			+ "ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. "
			+ "Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. "
			+ "Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis "
			+ "vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. "
			+ "Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque "
			+ "facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus "
			+ "non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod "
			+ "vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. Sed "
			+ "cursus turpis vitae tortor. Donec posuere vulputate arcu. Phasellus accumsan cursus "
			+ "velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia "
			+ "Curae; Sed aliquam, nisi quis porttitor congue, elit erat euismod orci, ac placerat "
			+ "dolor lectus quis orci. Phasellus consectetuer vestibulum elit. Aenean tellus metus, "
			+ "bibendum sed, posuere ac, mattis non, nunc. Vestibulum fringilla pede sit amet augue. "
			+ "In turpis. Pellentesque posuere. Praesent turpis. Aenean posuere, tortor sed cursus "
			+ "feugiat, nunc augue blandit nunc, eu sollicitudin urna dolor sagittis lacus. Donec "
			+ "elit libero, sodales nec, volutpat a, suscipit non, turpis. Nullam sagittis. ";
}