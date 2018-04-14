package cat.reisigualada.reis.printer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.List;

public class PagePrintable implements Printable {

	private List<PrintableLine> lines;
	
	public PagePrintable(List<PrintableLine> lines){
		this.lines = lines;
	}	
	public List<PrintableLine> getLines() {
		return lines;
	}
	public void setLines(List<PrintableLine> lines) {
		this.lines = lines;
	}

	public int print(Graphics g, PageFormat pageFormat, int page) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        for (PrintableLine pL : lines){
    		g2d.setColor(pL.getColor());
    		g2d.setFont(pL.getFont());
    		g2d.drawString(pL.getText(), pL.getPosX(), pL.getPosY());
    	}
        return (PAGE_EXISTS);
    }
}