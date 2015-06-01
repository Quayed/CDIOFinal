package dtu.cdio_final.shared.dto;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class OperatorDTO {
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	private int oprID;
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	private String oprName;
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	private String ini;
	/** Operatoer cpr-nr 10 karakterer */
	private String cpr;
	/** Operatoer password min. 7 max. 8 karakterer */
	private String password;

	public OperatorDTO(int oprID, String oprName, String ini, String cpr, String password) {
		this.oprID = oprID;
		this.oprName = oprName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
	}

	public OperatorDTO(OperatorDTO opr) {
		this.oprID = opr.getOprID();
		this.oprName = opr.getOprName();
		this.ini = opr.getIni();
		this.cpr = opr.getCpr();
		this.password = opr.getPassword();
	}

	public int getOprID() {
		return oprID;
	}
	public void setOprID(int oprID) {
		this.oprID = oprID;
	}
	
	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return oprID + "\t" + oprName + "\t" + ini + "\t" + cpr + "\t" + password;
	}

}
