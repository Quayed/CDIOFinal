package dtu.cdio_final.server.dal.daointerfaces;

public class DALException extends Exception {
	private static final long serialVersionUID = 1L;

	public DALException(String message) {
		super(message);
	}

	public DALException(Exception e) {
		super(e);
	}
}
