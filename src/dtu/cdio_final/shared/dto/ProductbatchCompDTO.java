package dtu.cdio_final.shared.dto;

import java.io.Serializable;

public class ProductbatchCompDTO implements Serializable {
	private int pbID; // produktbatchets id
	private int mbID; // i omraadet 1-99999999
	private int userID; // operatoer-nummer
	private double tare;
	private double netto;

	public ProductbatchCompDTO() {
	}
	
	public ProductbatchCompDTO(int pbID, int mbID, int userID, double tare, double netto) {
		this.pbID = pbID;
		this.mbID = mbID;
		this.userID = userID;
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

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
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
		return pbID + "\t" + mbID + "\t" + "\t" + userID + tare + "\t" + netto;
	}
}
