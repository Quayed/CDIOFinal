package dtu.cdio_final.shared.dto;

public class FormulaCompDTO {
	private int formulaID; // auto genereres fra 1..n
	private int materialID; // i omraadet 1-99999999
	private double nomNetto; // skal vaere positiv og passende stor
	private double tolerance; // skal vaere positiv og passende stor

	public FormulaCompDTO(int formulaID, int materialID, double nomNetto, double tolerance) {
		this.formulaID = formulaID;
		this.materialID = materialID;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getFormulaID() {
		return formulaID;
	}

	public void setFormulaID(int formulaID) {
		this.formulaID = formulaID;
	}

	public int getMaterialID() {
		return materialID;
	}

	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public double getNomNetto() {
		return nomNetto;
	}

	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public String toString() {
		return formulaID + "\t" + materialID + "\t" + nomNetto + "\t" + tolerance;
	}
}
