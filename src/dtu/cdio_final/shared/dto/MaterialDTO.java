package dtu.cdio_final.shared.dto;

/**
 * Raavare Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */

public class MaterialDTO {
	/** i omraadet 1-99999999 vaelges af brugerne */
	private int materialID;
	/** min. 2 max. 20 karakterer */
	private String materialName;
	/** min. 2 max. 20 karakterer */
	private String provider;

	public MaterialDTO(int materialID, String materialName, String provider) {
		this.materialID = materialID;
		this.materialName = materialName;
		this.provider = provider;
	}

	public int getMaterialID() {
		return materialID;
	}

	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String toString() {
		return materialID + "\t" + materialName + "\t" + provider;
	}
}
