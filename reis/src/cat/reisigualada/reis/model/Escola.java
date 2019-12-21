package cat.reisigualada.reis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "escola")
public class Escola {

	private Long id;
    private String descripcio;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Escola [id=" + id);
		builder.append(", descripcio=" + descripcio);
		builder.append("]");
		return builder.toString();
	}
}
