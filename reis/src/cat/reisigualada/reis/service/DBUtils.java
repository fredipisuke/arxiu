package cat.reisigualada.reis.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import cat.reisdigualada.reis.vo.EstadistiquesVO;
import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.utils.AjaxResponseBody;
import cat.reisigualada.reis.utils.Constants;
import cat.reisigualada.reis.web.arxiu.SearchCriteriaFitxers;

public class DBUtils {
	
	public static Long getMaxIdFitxer(){
		String QUERY = "select max(id) from fitxer";
		System.out.println("DBUtils.getMaxIdFitxer: " + QUERY);
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery(QUERY);
	        while (rs.next()) {
	            return rs.getLong(1) + 1;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		return null;
	}
	
	public static EstadistiquesVO getStatistics(){
		EstadistiquesVO estadistiques = new EstadistiquesVO();
		
		// TOTAL IMATGES
		String QUERY = "select count(1) from fitxer where typeDocument = " + Constants.TYPE_KEY_IMAGE;
		System.out.println("DBUtils.getMaxIdFitxer: " + QUERY);
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery(QUERY);
	        while (rs.next()) {
	        	estadistiques.setTotalImatges(rs.getLong(1));
	            break;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		
		// TOTAL DOCUMENTS
		QUERY = "select count(1) from fitxer where typeDocument = " + Constants.TYPE_KEY_DOCUMENTS;
		System.out.println("DBUtils.getMaxIdFitxer: " + QUERY);
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery(QUERY);
	        while (rs.next()) {
	        	estadistiques.setTotalDocuments(rs.getLong(1));
	            break;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		return estadistiques;
	}
	
	public static AjaxResponseBody searchForWellcome(SearchCriteriaFitxers criteria){
		AjaxResponseBody result = new AjaxResponseBody();
		String SELECT_FITXERS = "select distinct f.* from fitxer f where 1=1";
		// CONDICIONANTS
		SELECT_FITXERS += mountWheres(criteria);
		// ORDENACI�
		SELECT_FITXERS += " order by rand() ";
		// L�MITS I N�MERO D'ELEMENTS
		SELECT_FITXERS += " LIMIT 4 ";
		
		// EJECUTAMOS LA QUERY
		List<Fitxer> listF = new ArrayList<Fitxer>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.searchByCriteria: " + SELECT_FITXERS);
		    ResultSet rs = st.executeQuery(SELECT_FITXERS);
	        while (rs.next()) {
	        	Fitxer f = new Fitxer();
	        	f.setId(rs.getLong("id"));
	        	f.setTitol(rs.getString("titol"));
	        	f.setTypeDocument(rs.getLong("typeDocument"));
	        	f.setObservacions(rs.getString("observacions"));
	        	f.setFileName(rs.getString("fileName"));
	        	f.setFormat(rs.getString("format"));
	        	f.setData(rs.getDate("data"));
	        	f.setAutor_id(rs.getLong("autor_id"));
	        	f.setDataCreacio(rs.getTimestamp("dataCreacio"));
	        	f.setUbicacio(rs.getString("ubicacio"));
	        	f.setUbicacioArxiu(rs.getString("ubicacioArxiu"));
	        	f.setReferencia(rs.getString("referencia"));
	        	// Obtenim el llistat de claus del fitxer
	        	if(criteria.isSearchKeys()){
	        		f.setClaus(searchClaus(f));
	        	}
	        	listF.add(f);
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		result.setResult(listF);
		
		return result;
	}
	
	public static List<Fitxer> searchForDocument(SearchCriteriaFitxers criteria){
		String SELECT_FITXERS = "select distinct f.* from fitxer f where 1=1";
		// CONDICIONANTS
		SELECT_FITXERS += mountWheres(criteria);
		// ORDENACI�
		SELECT_FITXERS += " order by id ";
		// L�MITS I N�MERO D'ELEMENTS
		if(criteria.getnElementsPerPage()!=null){
			Long offset = criteria.getnElementsPerPage() * criteria.getPagina();
			SELECT_FITXERS += " LIMIT " + criteria.getnElementsPerPage() + " OFFSET " + offset;
		}
		
		// EJECUTAMOS LA QUERY
		List<Fitxer> listF = new ArrayList<Fitxer>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.searchByCriteria: " + SELECT_FITXERS);
		    ResultSet rs = st.executeQuery(SELECT_FITXERS);
	        while (rs.next()) {
	        	Fitxer f = new Fitxer();
	        	f.setId(rs.getLong("id"));
	        	f.setTitol(rs.getString("titol"));
	        	f.setTypeDocument(rs.getLong("typeDocument"));
	        	f.setObservacions(rs.getString("observacions"));
	        	f.setFileName(rs.getString("fileName"));
	        	f.setFormat(rs.getString("format"));
	        	f.setData(rs.getDate("data"));
	        	f.setAutor_id(rs.getLong("autor_id"));
	        	f.setDataCreacio(rs.getTimestamp("dataCreacio"));
	        	f.setUbicacio(rs.getString("ubicacio"));
	        	f.setUbicacioArxiu(rs.getString("ubicacioArxiu"));
	        	f.setReferencia(rs.getString("referencia"));
	        	// Obtenim el llistat de claus del fitxer
	        	if(criteria.isSearchKeys()){
	        		f.setClaus(searchClaus(f));
	        	}
	        	listF.add(f);
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		return listF;
	}
	
	public static AjaxResponseBody searchForView(SearchCriteriaFitxers criteria){
		AjaxResponseBody result = new AjaxResponseBody();
		String SELECT_FITXERS = "select distinct f.* from fitxer f where 1=1";
		// CONDICIONANTS
		SELECT_FITXERS += mountWheres(criteria);
		// ORDENACI�
		SELECT_FITXERS += " order by id ";
		// L�MITS I N�MERO D'ELEMENTS
		if(criteria.getnElementsPerPage()!=null){
			Long offset = criteria.getnElementsPerPage() * criteria.getPagina();
			SELECT_FITXERS += " LIMIT " + criteria.getnElementsPerPage() + " OFFSET " + offset;
		}
		
		// EJECUTAMOS LA QUERY
		List<Fitxer> listF = new ArrayList<Fitxer>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.searchByCriteria: " + SELECT_FITXERS);
		    ResultSet rs = st.executeQuery(SELECT_FITXERS);
	        while (rs.next()) {
	        	Fitxer f = new Fitxer();
	        	f.setId(rs.getLong("id"));
	        	f.setTitol(rs.getString("titol"));
	        	f.setTypeDocument(rs.getLong("typeDocument"));
	        	f.setObservacions(rs.getString("observacions"));
	        	f.setFileName(rs.getString("fileName"));
	        	f.setFormat(rs.getString("format"));
	        	f.setData(rs.getDate("data"));
	        	f.setAutor_id(rs.getLong("autor_id"));
	        	f.setDataCreacio(rs.getTimestamp("dataCreacio"));
	        	f.setUbicacio(rs.getString("ubicacio"));
	        	f.setUbicacioArxiu(rs.getString("ubicacioArxiu"));
	        	f.setReferencia(rs.getString("referencia"));
	        	// Obtenim el llistat de claus del fitxer
	        	if(criteria.isSearchKeys()){
	        		f.setClaus(searchClaus(f));
	        	}
	        	listF.add(f);
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		result.setResult(listF);
		
		// CARGAMOS EL CONTADOR DE ELEMNTOS TOTALES
		SELECT_FITXERS = "select count(id) num_total from fitxer f where 1=1";
		// CONDICIONANTS
		SELECT_FITXERS += mountWheres(criteria);
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.searchByCriteria: " + SELECT_FITXERS);
		    ResultSet rs = st.executeQuery(SELECT_FITXERS);
	        while (rs.next()) {
	        	result.setTotal(rs.getLong("num_total"));
	        	break;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static String mountWheres(SearchCriteriaFitxers criteria){
		String WHERES = "";
		if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau()) && criteria.getClaus()!=null && criteria.getClaus().size()>0){
			for(Clau c : criteria.getClaus()){
				WHERES += " and exists (select * from fitxer_clau fc where f.id=fc.fitxer_id and fc.clau_id=" + c.getId() + ")";
			}
		}
		if(criteria.getFileName()!=null && !"".equals(criteria.getFileName())){
			WHERES += " and fileName like '%" + criteria.getFileName() + "%'";
		}
		if(criteria.getReferencia()!=null && !"".equals(criteria.getReferencia())){
			WHERES += " and referencia like '%" + criteria.getReferencia() + "%'";
		}
		if(criteria.getTitol()!=null && !"".equals(criteria.getTitol())){
			WHERES += " and titol like '%" + criteria.getTitol() + "%' ";
		}
		if(criteria.getYear()!=null){
			WHERES += " and year(f.data) = " + criteria.getYear();
		}
		if(criteria.getTypeDocument()!=null && !"".equals(criteria.getTypeDocument())){
			WHERES += " and typeDocument = " + criteria.getTypeDocument();
		}
		if(criteria.getAutor_id()!=null && !"".equals(criteria.getAutor_id())){
			WHERES += " and autor_id = " + criteria.getAutor_id();
		}
		return WHERES;
	}
	
	public static HashSet<Clau> searchClaus(Fitxer f){
		String SELECT_CLAUS = "select c.* from clau c "
							+ " left join fitxer_clau fc ON c.id = fc.clau_id "
							+ " where fitxer_id = " + f.getId();
		
		SELECT_CLAUS += " order by name ";
		System.out.println("DBUtils.searchClaus: " + SELECT_CLAUS);
		
		// EJECUTAMOS LA QUERY
		HashSet<Clau> hsC = new HashSet<Clau>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery(SELECT_CLAUS);
	        while (rs.next()) {
	        	Clau c = new Clau();
	        	c.setId(rs.getLong("id"));
	        	c.setName(rs.getString("name"));
	        	c.setType(rs.getLong("type"));
	        	hsC.add(c);
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			e.printStackTrace();
		}
		return hsC;
	}
	
	public static String generateBackUp() throws Exception {
		String BACK_UP = "";
		
		// AUTOR
		try {
			String SELECT_AUTORS = "select * from autor";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.generateBackUp: " + SELECT_AUTORS);
		    ResultSet rs = st.executeQuery(SELECT_AUTORS);
			int i=0;
	        while (rs.next()) {
	        	if(i==0){
	        		BACK_UP += "insert into autor (id, name) values \n";
	        	}
	        	BACK_UP += "(" + rs.getLong("id")
	        	 			+ ", '" + dbScape(rs.getString("name")) + "')";
	        	if(rs.isLast()){
	        		BACK_UP += ";\n";
	        	} else {
	        		BACK_UP += ",\n";
	        	}
	        	i++;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			System.out.println("DBUtils.generateBackUp, Error: " + e.getMessage());
		}		
		
		// CLAU
		try {
			String SELECT_CLAUS = "select * from clau";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.generateBackUp: " + SELECT_CLAUS);
		    ResultSet rs = st.executeQuery(SELECT_CLAUS);
			int i=0;
	        while (rs.next()) {
	        	if(i==0){
	        		BACK_UP += "\ninsert into clau (id, name, type) values \n";
	        	}
	        	BACK_UP += "(" + rs.getLong("id")
	        	 			+ ", '" + dbScape(rs.getString("name")) + "'"
				        	+ ", " + rs.getLong("type") + ")";
	        	if(rs.isLast()){
	        		BACK_UP += ";\n";
	        	} else {
	        		BACK_UP += ",\n";
	        	}
	        	i++;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			System.out.println("DBUtils.generateBackUp, Error: " + e.getMessage());
		}

	    // FITXERS
		try {
			String SELECT_FITXERS = "select * from fitxer";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.generateBackUp: " + SELECT_FITXERS);
		    ResultSet rs = st.executeQuery(SELECT_FITXERS);
			int i=0;
	        while (rs.next()) {
	        	if(i==0){
	        		BACK_UP += "\ninsert into fitxer (id, titol, typeDocument, observacions, fileName, format, data, autor_id, dataCreacio, ubicacio, referencia, ubicacioArxiu) values \n";
	        	}
	        	BACK_UP += "(" + rs.getLong("id")
	        	 			+ ", '" + dbScape(rs.getString("titol")) + "'"
				        	+ ", " + rs.getLong("typeDocument")
				        	+ ", '" + dbScape(rs.getString("observacions")) + "'"
				        	+ ", '" + rs.getString("fileName") + "'"
				        	+ ", '" + rs.getString("format") + "'"
				        	+ ", '" + rs.getDate("data") + "'"
				        	+ ", " + rs.getLong("autor_id")
				        	+ ", '" + rs.getTimestamp("dataCreacio") + "'"
				        	+ ", '" + dbScape(rs.getString("ubicacio")) + "'"
				        	+ ", '" + dbScape(rs.getString("ubicacioArxiu")) + "'"
				        	+ ", '" + dbScape(rs.getString("referencia")) + "')";
	        	if(rs.isLast()){
	        		BACK_UP += ";\n";
	        	} else {
	        		BACK_UP += ",\n";
	        	}
	        	i++;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			System.out.println("DBUtils.generateBackUp, Error: " + e.getMessage());
		}

		// FITXERS-CLAU
		try {
			String SELECT_FTXER_CLAU = "select * from fitxer_clau";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
			System.out.println("DBUtils.generateBackUp: " + SELECT_FTXER_CLAU);
		    ResultSet rs = st.executeQuery(SELECT_FTXER_CLAU);
			int i=0;
	        while (rs.next()) {
	        	if(i==0){
	        		BACK_UP += "\ninsert into fitxer_clau (fitxer_id, clau_id) values \n";
	        	}
	        	BACK_UP += "(" + rs.getLong("fitxer_id")
				        	+ ", " + rs.getLong("clau_id") + ")";
	        	if(rs.isLast()){
	        		BACK_UP += ";\n";
	        	} else {
	        		BACK_UP += ",\n";
	        	}
	        	i++;
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch(Exception e){ 
			System.out.println("DBUtils.generateBackUp, Error: " + e.getMessage());
		}
				
		System.out.println("DBUtils.generateBackUp: result " + BACK_UP);
	    return BACK_UP;
	}
	
	private static String dbScape(String value){
		value = value.replaceAll("'", "''");
		value = value.replaceAll("\r\n", "\\\\r\\\\n");
		value = value.replaceAll("\n", "\\\\n");
		return value;
	}
}