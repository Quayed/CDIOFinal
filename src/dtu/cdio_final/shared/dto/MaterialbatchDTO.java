package dtu.cdio_final.shared.dto;

import java.io.Serializable;

public class MaterialbatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int mbID; // i omraadet 1-99999999
	private int materialID; // i omraadet 1-99999999
	private double quantity; // kan vaere negativ

	public MaterialbatchDTO() {
	}
	
	public MaterialbatchDTO(int mbID, int materialID, double quantity) {
		this.mbID = mbID;
		this.materialID = materialID;
		this.quantity = quantity;
	}

	public int getMbID() {
		return mbID;
	}

	public void setMbID(int mbID) {
		this.mbID = mbID;
	}

	public int getMaterialID() {
		return materialID;
	}

	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return mbID + "\t" + materialID + "\t" + quantity;
	}
}
