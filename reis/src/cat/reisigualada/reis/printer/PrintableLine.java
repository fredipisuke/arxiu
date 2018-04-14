package cat.reisigualada.reis.printer;

import java.awt.Color;
import java.awt.Font;

public class PrintableLine {

	private int posX;
	private int posY;
	private Font font = new Font("helvetica", Font.PLAIN, 10);
	private Color color = Color.BLACK;
	private String text;
	
	public PrintableLine(int posX, int posY, String text, Font font, Color color){
		this.posX = posX;
		this.posY = posY;
		this.text = text;
		if(font!=null)
			this.font = font;
		if(color!=null)
			this.color = color;
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrintableLine [posX=");
		builder.append(posX);
		builder.append(", posY=");
		builder.append(posY);
		builder.append(", font=");
		builder.append(font);
		builder.append(", color=");
		builder.append(color);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}

}
