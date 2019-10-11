package cat.reisigualada.reis.vo;

public class EstadistiquesVO {
	
	private Long totalImatges = new Long(0);
	private Long totalDocuments = new Long(0);
	private Long totalDigitals = new Long(0);
	
	public Long getTotalImatges() {
		return totalImatges;
	}
	public void setTotalImatges(Long totalImatges) {
		this.totalImatges = totalImatges;
	}
	public Long getTotalDocuments() {
		return totalDocuments;
	}
	public void setTotalDocuments(Long totalDocuments) {
		this.totalDocuments = totalDocuments;
	}
	public Long getTotalDigitals() {
		return totalDigitals;
	}
	public void setTotalDigitals(Long totalDigitals) {
		this.totalDigitals = totalDigitals;
	}
}
