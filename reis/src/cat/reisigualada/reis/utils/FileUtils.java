package cat.reisigualada.reis.utils;

import java.util.UUID;

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
}
