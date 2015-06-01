package dtu.cdio_final.shared.dto;

public class ProductbatchDTO {
	private int pbID; // i omraadet 1-99999999
	private int formulaID;
	private int status; // 0: ikke paabegyndt, 1: under produktion, 2: afsluttet

	public ProductbatchDTO(int pbID, int formulaID, int status) {
		this.pbID = pbID;
		this.formulaID = formulaID;
		this.status = status;
	}

	public int getPbID() {
		return pbID;
	}

	public void setPbId(int pbID) {
		this.pbID = pbID;
	}

	public int getFormulaID() {
		return formulaID;
	}

	public void setFormulaID(int formulaID) {
		this.formulaID = formulaID;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return pbID + "\t" + formulaID + "\t" + status;
	}
}
