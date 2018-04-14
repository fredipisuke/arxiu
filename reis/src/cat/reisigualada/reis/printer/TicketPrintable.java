package cat.reisigualada.reis.printer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class TicketPrintable implements Printable {

	private static final int MARGIN_SIZE = 72;
	private static String ETIQUETA = 	"ETIQUETA REIS\n"
							            + "____________________________\n"
							            + "Marc, Joan i Estel\n"
							            + "No: 112\n"
							            + "C/ Gaspar Camps 25, 2n 1a\n"
							            + "____________________________\n";
    
	public int print(Graphics g, PageFormat pageFormat, int page) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        // Only on the first two documents...
        if (page <= 1) {
            // Marges
        	int x = MARGIN_SIZE;
        	int y = MARGIN_SIZE;
        	for (String line : ETIQUETA.split("\n"))
                g2d.drawString(line, x, y += g.getFontMetrics().getHeight());
            return (PAGE_EXISTS);
        }
        return (NO_SUCH_PAGE);
    }
}