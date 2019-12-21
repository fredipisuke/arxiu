package cat.reisigualada.reis.web.nens;

public class SearchCriteriaNens {

	private Long id;
	private String nom;
	private String sexe;
    private Long edat;
	private Long escola_id;
	private String escola;
    // Parametres del cercador
    private Long pagina = new Long(0);
    private Long nElementsPerPage = new Long(10);    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public Long getEdat() {
		return edat;
	}
	public void setEdat(Long edat) {
		this.edat = edat;
	}
	public Long getEscola_id() {
		return escola_id;
	}
	public void setEscola_id(Long escola_id) {
		this.escola_id = escola_id;
	}
	public String getEscola() {
		return escola;
	}
	public void setEscola(String escola) {
		this.escola = escola;
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