package cat.reisigualada.reis.utils;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;
import javax.imageio.ImageIO;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.service.DBUtils;

public class FileUtils {

    public static String generateString() {
    	return UUID.randomUUID().toString();
    }
    
	public static String generateFileName(Fitxer f){
    	Long id = f.getId();
    	if(id==null)
    		id = DBUtils.getMaxIdFitxer();

    	String name = "";
    	if(f.getTypeDocument().equals(Constants.TYPE_KEY_IMAGE)){
    		name = "PIC_" + f.getYear() + "_" + generateCode(id, 10);
    	} else if(f.getTypeDocument().equals(Constants.TYPE_KEY_DOCUMENTS)){
    		name = "DOC_" + f.getYear() + "_" + generateCode(id, 10);
    	}    	
    	return name;
    }
    
    private static String generateCode(Long id, int longitud){
    	String code = id.toString();
    	for(int i=id.toString().length(); i<longitud; i++){
    		code = "0" + code;
    	}
    	return code;
    }
    
    public static void createThumbnails(Fitxer f) throws Exception {
    	BufferedImage img = ImageIO.read(new File(Constants.UPLOADED_FOLDER + f.getFileName() + "." + f.getFormat()));
		BufferedImage scaled = scale(img);
		ImageIO.write(scaled, "jpg", new File(Constants.UPLOADED_FOLDER_THUMBNAILS + f.getFileName() + "." + f.getFormat()));
    }
    
	private static BufferedImage scale(BufferedImage source) {
		int w = 250;
		int h = (int) (source.getHeight() * w / source.getWidth());
		BufferedImage bi = getCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) w / source.getWidth();
		double yScale = (double) h / source.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}

	private static BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}
}
