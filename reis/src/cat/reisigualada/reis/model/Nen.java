package cat.reisigualada.reis.model;

import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "nen")
public class Nen {
	
	@EmbeddedId 
	private Long id;
	private String tipusDocument;
	private String document;
    private String nom;
    private String sexe;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNaixement;
    private String observacionsAmics;
    private String observacionsJeep;
    private boolean surt;
    private boolean caramelsPagats;
    @Transient
    private Escola escola = new Escola();
    @Transient
    private Tutor tutor = new Tutor();
    
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
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public Date getDataNaixement() {
		return dataNaixement;
	}
	public void setDataNaixement(Date dataNaixement) {
		this.dataNaixement = dataNaixement;
	}
	public String getObservacionsAmics() {
		return observacionsAmics;
	}
	public void setObservacionsAmics(String observacionsAmics) {
		this.observacionsAmics = observacionsAmics;
	}
	public String getObservacionsJeep() {
		return observacionsJeep;
	}
	public void setObservacionsJeep(String observacionsJeep) {
		this.observacionsJeep = observacionsJeep;
	}
	public boolean isSurt() {
		return surt;
	}
	public void setSurt(boolean surt) {
		this.surt = surt;
	}
	public boolean isCaramelsPagats() {
		return caramelsPagats;
	}
	public void setCaramelsPagats(boolean caramelsPagats) {
		this.caramelsPagats = caramelsPagats;
	}
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "escola", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "escola_id") })   
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "tutor", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "tutor_id") })   
	public Tutor getTutor() {
		return tutor;
	}
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Nen id=" + id);
		builder.append(", tipusDocument=" + tipusDocument);
		builder.append(", document=" + document);
		builder.append(", nom=" + nom);
		builder.append(", sexe=" + sexe);
		builder.append(", dataNaixement=" + dataNaixement);
		builder.append(", observacionsAmics=" + observacionsAmics);
		builder.append(", observacionsJeep=" + observacionsJeep);
		builder.append(", surt=" + surt);
		builder.append(", caramelsPagats=" + caramelsPagats);
		builder.append(", escola=" + escola);
		builder.append(", tutor=" + tutor);
		return builder.toString();
	}
}