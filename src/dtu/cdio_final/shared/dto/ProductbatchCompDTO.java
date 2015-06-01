package dtu.cdio_final.shared.dto;

public class ProductbatchCompDTO {
	private int pbID; // produktbatchets id
	private int mbID; // i omraadet 1-99999999
	private int oprID; // operatoer-nummer
	private double tare;
	private double netto;

	public ProductbatchCompDTO(int pbID, int mbID, int oprID, double tare, double netto) {
		this.pbID = pbID;
		this.mbID = mbID;
		this.oprID = oprID;
		this.tare = tare;
		this.netto = netto;
		
	}

	public int getPbID() {
		return pbID;
	}

	public void setPbID(int pbID) {
		this.pbID = pbID;
	}

	public int getMbID() {
		return mbID;
	}

	public void setMbID(int mbID) {
		this.mbID = mbID;
	}

	public int getOprID() {
		return oprID;
	}

	public void setOprID(int oprID) {
		this.oprID = oprID;
	}
	
	public double getTare() {
		return tare;
	}

	public void setTare(double tare) {
		this.tare = tare;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public String toString() {
		return pbID + "\t" + mbID + "\t" + "\t" + oprID + tare + "\t" + netto;
	}
}
