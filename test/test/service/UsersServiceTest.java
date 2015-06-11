package test.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.service.DataServiceImpl;
import dtu.cdio_final.shared.ServiceException;
import dtu.cdio_final.shared.TokenException;
import dtu.cdio_final.shared.TokenHandler;
import dtu.cdio_final.shared.dto.UserDTO;

public class UsersServiceTest {

	private static DataService service;
	private static String token;
	
	@BeforeClass
	public static void before(){
		service = new DataServiceImpl();
		token = TokenHandler.getInstance().createToken(""+1);
	}
	
	@Test
	public void createUser() {
		try {
			service.createUser(token, new UserDTO(87, "test name", "tn", "1234567834", "qHsdp34!", 3, 1));
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateUser() {
		try {
			service.updateUser(token, new UserDTO(87, "test name", "tn", "1234567834", "qHsdp34!", 3, 1));
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getUser() {
		try {
			service.getUsers(token);
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void after(){
		try {
			Connector.doUpdate("DELETE FROM user WHERE user_id = 87");
		} catch (SQLException e) {}
	}

}
