package dtu.cdio_final.shared.dto;

import java.io.Serializable;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges
	 * af brugerne
	 */
	private int userID;
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	private String userName;
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	private String ini;
	/** Operatoer cpr-nr 10 karakterer */
	private String cpr;
	/** Operatoer password min. 7 max. 8 karakterer */
	private String password;

	private int role;
	private int status;

	public UserDTO() {
	}

	public UserDTO(int userID, String userName, String ini, String cpr, String password, int role, int status) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setIni(ini);
		this.setCpr(cpr);
		this.setPassword(password);
		this.setRole(role);
		this.setStatus(status);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		return userID + "\t" + userName + "\t" + ini + "\t" + cpr + "\t" + password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
