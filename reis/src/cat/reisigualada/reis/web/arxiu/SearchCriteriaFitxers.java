package cat.reisigualada.reis.web.arxiu;

import java.util.Set;

import cat.reisigualada.reis.model.Clau;

public class SearchCriteriaFitxers {

	private String titol;
    private Long year;
    private Long typeDocument;
    private String paraulesClau;
    private Set<Clau> claus;
    private boolean searchKeys = false;
    
	public String getTitol() {
		return titol;
	}
	public void setTitol(String titol) {
		this.titol = titol;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(Long typeDocument) {
		this.typeDocument = typeDocument;
	}
	public String getParaulesClau() {
		return paraulesClau;
	}
	public void setParaulesClau(String paraulesClau) {
		this.paraulesClau = paraulesClau;
	}
	public Set<Clau> getClaus() {
		return claus;
	}
	public void setClaus(Set<Clau> claus) {
		this.claus = claus;
	}
	public boolean isSearchKeys() {
		return searchKeys;
	}
	public void setSearchKeys(boolean searchKeys) {
		this.searchKeys = searchKeys;
	}
}
