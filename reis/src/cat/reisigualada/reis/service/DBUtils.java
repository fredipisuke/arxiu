package cat.reisigualada.reis.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.model.Fitxer;
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
		    ResultSet rs = st.executeQuery("QUERY");
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
	
	public static List<Fitxer> searchByCriteria(SearchCriteriaFitxers criteria){
		String SELECT_FITXERS = "select distinct f.* from fitxer f where 1=1";
		if(criteria.getParaulesClau()!=null && !"".equals(criteria.getParaulesClau()) && criteria.getClaus()!=null && criteria.getClaus().size()>0){
			for(Clau c : criteria.getClaus()){
				SELECT_FITXERS += " and exists (select * from fitxer_clau fc where f.id=fc.fitxer_id and fc.clau_id=" + c.getId() + ")";
			}
		}
		if(criteria.getYear()!=null){
			SELECT_FITXERS += " and year(f.data) = " + criteria.getYear();
		}
		if(criteria.getTitol()!=null && !"".equals(criteria.getTitol())){
			SELECT_FITXERS += " and titol like '%" + criteria.getTitol() + "%' ";
		}
		if(criteria.getTypeDocument()!=null && !"".equals(criteria.getTypeDocument())){
			SELECT_FITXERS += " and typeDocument = " + criteria.getTypeDocument();
		}
		
		SELECT_FITXERS += " order by id ";
		System.out.println("DBUtils.searchByCriteria: " + SELECT_FITXERS);
		
		// EJECUTAMOS LA QUERY
		List<Fitxer> listF = new ArrayList<Fitxer>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reisigualada", "reis", "reisigualada");
		    Statement st = conn.createStatement();
		    st = conn.createStatement();
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
	        	f.setAutor(rs.getString("autor"));
	        	f.setDataCreacio(rs.getTimestamp("dataCreacio"));
	        	f.setUbicacio(rs.getString("ubicacio"));
	        	f.setUbicacioArxiu(rs.getString("ubicacioArxiu"));
	        	f.setProcedencia(rs.getString("procedencia"));
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
}
