package cat.reisigualada.reis.web.arxiu;

import java.util.Set;

import cat.reisigualada.reis.model.Clau;

public class SearchCriteriaFitxers {

	private Long id;
	private String fileName;
	private String referencia;
	private String titol;
    private Long year;
    private Long typeDocument;
    private String paraulesClau;
    private Set<Clau> claus;
    private boolean searchKeys = false;
    private Long autor_id;
    // Parametres del cercador
    private Long pagina = new Long(0);
    private Long nElementsPerPage = new Long(10);    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
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
	public Long getAutor_id() {
		return autor_id;
	}
	public void setAutor_id(Long autor_id) {
		this.autor_id = autor_id;
	}
	public Long getPagina() {
		return pagina;
	}
	public void setPagina(Long pagina) {
		this.pagina = pagina;
	}
	public Long getnElementsPerPage() {
		return nElementsPerPage;
	}
	public void setnElementsPerPage(Long nElementsPerPage) {
		this.nElementsPerPage = nElementsPerPage;
	}
}
