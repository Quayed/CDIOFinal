package dtu.cdio_final.server.dal.daointerfaces;

import java.sql.SQLException;

public class DALException extends Exception {
	private static final long serialVersionUID = 1L;

	public DALException(String message) {
		super(message);
	}

	public DALException(SQLException e) {
		super(e);
		// TODO: implement special messages for SQL
	}
	
	public DALException(Exception e) {
		super(e);
	}
}
