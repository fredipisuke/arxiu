package cat.reisigualada.reis.utils;

import java.util.List;
import cat.reisigualada.reis.model.Clau;

public class ListUtils {

	public static String listClausToString(List<Clau> listClaus){
		String txt = "";
		for(Clau key : listClaus){
			if("".equals(txt)){
				txt = "'" + key.getName() + "'";
			} else {
				txt += ",'" + key.getName() + "'";
			}
		}
		return txt;
	}
}
