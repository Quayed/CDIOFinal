package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.UserDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IUserDAO;
import dtu.cdio_final.shared.dto.UserDTO;

public class TestOperatorDAO{

	private static int insertID;
	
	private static IUserDAO operatorDAO;
	private UserDTO operatorDTO;
	
	@BeforeClass
	public static void connect() {
				
		operatorDAO = new UserDAO();

	}
	
	@Test
	public void getOperator() {
		try {
			operatorDTO = operatorDAO.getUser(3);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		assertTrue(operatorDTO != null);
	}
	
	@Test
	public void getOperatorList() {
		try {
			operatorDAO.getUserList();
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void createUpdateOperator() {
		operatorDTO = new UserDTO(0, "Don Juan", "DJ", "000000-0000", "iloveyou");
		try {
			operatorDAO.createUser(operatorDTO);
			insertID = operatorDTO.getUserID();
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			operatorDTO = operatorDAO.getUser(insertID);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		operatorDTO.setIni("DoJu");
		try {
			operatorDAO.updateUser(operatorDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertEquals(operatorDTO.getIni(), operatorDAO.getUser(insertID).getIni());
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertNull(operatorDAO.getUser(insertID+1));
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM operator WHERE opr_id = "+insertID);
		} catch (SQLException e) {}
	}

}
