package cat.reisigualada.reis.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class FitxerKey implements Serializable {
	
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "typeDocument", nullable = false)
    private Long typeDocument;
    
    public FitxerKey(){
    	// Nothing to do
    }
    
	public FitxerKey(Long id, Long typeDocument){
		this.id = id;
		this.typeDocument = typeDocument;
	}

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(Long typeDocument) {
		this.typeDocument = typeDocument;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FitxerKey [id=" + id);
		builder.append(", typeDocument=" + typeDocument);
		return builder.toString();
	}
}