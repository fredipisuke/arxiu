package cat.reisigualada.reis.model;

import javax.persistence.*;

@Entity
@Table(name = "printer")
public class Printer {
    private Long id;
    private String printername;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getPrintername() {
		return printername;
	}

	public void setPrintername(String printername) {
		this.printername = printername;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Printer [id=");
		builder.append(id);
		builder.append(", printername=");
		builder.append(printername);
		builder.append("]");
		return builder.toString();
	}
}
