package cat.reisigualada.reis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tutor")
public class Tutor {

	private Long id;
	private String tipusDocument;
    private String document;
    private String nom;
    private String direccio;
    private String codiPostal;
    private String poblacio;
    private String telefon;
    private String mobil;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipusDocument() {
		return tipusDocument;
	}
	public void setTipusDocument(String tipusDocument) {
		this.tipusDocument = tipusDocument;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDireccio() {
		return direccio;
	}
	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}
	public String getCodiPostal() {
		return codiPostal;
	}
	public void setCodiPostal(String codiPostal) {
		this.codiPostal = codiPostal;
	}
	public String getPoblacio() {
		return poblacio;
	}
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getMobil() {
		return mobil;
	}
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tutor [id=" + id);
		builder.append(", tipusDocument=" + tipusDocument);
		builder.append(", document=" + document);
		builder.append(", nom=" + nom);
		builder.append(", direccio=" + direccio);
		builder.append(", codiPostal=" + codiPostal);
		builder.append(", poblacio=" + poblacio);
		builder.append(", telefon=" + telefon);
		builder.append(", mobil=" + mobil);
		builder.append(", email=" + email);
		builder.append("]");
		return builder.toString();
	}
}
