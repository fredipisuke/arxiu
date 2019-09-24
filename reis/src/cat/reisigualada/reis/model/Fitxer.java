package cat.reisigualada.reis.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import cat.reisigualada.reis.vo.FitxerKey;

@Entity
@Table(name = "fitxer")
public class Fitxer {
	
	@EmbeddedId 
	private FitxerKey pk = new FitxerKey();
    private String titol;
    @Transient
    private String paraulesClau;
    @Transient
    private ArrayList<Clau> claus;
    private String observacions;
    private String fileName;
    private String format;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
    private Long autor_id;
    @Transient
    private String autor;
    private Timestamp dataCreacio;
    // PROPIETATS D'IMATGE
    private String ubicacio;
    private String referencia;
    // PROPIETATS DE DOCUMENT
    private String ubicacioArxiu;

    public FitxerKey getPk(){
    	return pk;
    }
    public void setPk(FitxerKey pk){
    	this.pk = pk;
    }
    public String getTitol() {
		return titol;
	}
	public void setTitol(String titol) {
		this.titol = titol;
	}
	@Transient
	public String getParaulesClau() {
		if(claus!=null && claus.size()>0){
			String pC = "";
			for(Clau c : claus){
				if("".equals(pC)){
					pC += c.getName();
				} else {
					pC += ", " + c.getName();
				}
			}
			return pC;
		} else {
			if(paraulesClau==null) return "";
			else return paraulesClau;
		}
	}
	public void setParaulesClau(String paraulesClau) {
		this.paraulesClau = paraulesClau;
	}
	@Transient
    public ArrayList<Clau> getClaus() {
        return claus;
    }
	@Transient
    public void setClaus(ArrayList<Clau> claus) {
        this.claus = claus;
    }
	public String getObservacions() {
		if(observacions==null) return "";
		else return observacions;
	}
	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Long getAutor_id() {
		return autor_id;
	}
	public void setAutor_id(Long autor_id) {
		this.autor_id = autor_id;
	}
	public Timestamp getDataCreacio() {
		return dataCreacio;
	}
	public void setDataCreacio(Timestamp dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
	// PROPIETATS D'IMATGE
	public String getUbicacio() {
		return ubicacio;
	}
	public void setUbicacio(String ubicacio) {
		this.ubicacio = ubicacio;
	}
    // PROPIETATS DE DOCUMENT
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getUbicacioArxiu() {
		return ubicacioArxiu;
	}
	public void setUbicacioArxiu(String ubicacioArxiu) {
		this.ubicacioArxiu = ubicacioArxiu;
	}

	@Transient
	public String getAutor() {
		return autor;
	}
	@Transient
	public void setAutor(String autor) {
		this.autor = autor;
	}
	@Transient
    public String getTitolResum() {
		if(titol!=null){
			if(titol.length()>18){
				return titol.substring(0, 18) + "...";
			} else {
				return titol;	
			}
		} else {
			return null;
		}
	}
	@Transient
	public int getYear(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return cal.get(Calendar.YEAR);
	}
	@Transient
    public String getObservacionsResum() {
		if(observacions!=null){
			if(observacions.length()>200){
				return observacions.substring(0, 200) + "...";
			} else {
				return observacions;	
			}
		} else {
			return null;
		}
	}
	@Transient
    public String getObservacionsWellcome() {
		if(observacions!=null){
			if(observacions.length()>75){
				return observacions.substring(0, 75) + "...";
			} else {
				return observacions;	
			}
		} else {
			return null;
		}
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Fitxer [FitxerKey=" + pk);
		builder.append(", titol=" + titol);
		builder.append(", paraulesClau=" + paraulesClau);
		builder.append(", fileName=" + fileName);
		builder.append(", format=" + format);
		builder.append(", data=" + data);
		builder.append(", autor_id=" + autor_id);
		builder.append(", dataCreacio=" + dataCreacio);
	    // PROPIETATS D'IMATGE
		builder.append(", ubicacio=" + ubicacio);
		builder.append(", referencia=" + referencia);
	    // PROPIETATS DE DOCUMENT
		builder.append(", ubicacioArxiu=" + ubicacioArxiu);
		return builder.toString();
	}
}