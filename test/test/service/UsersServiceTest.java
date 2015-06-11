package test.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.server.service.DataServiceImpl;
import dtu.cdio_final.shared.ServiceException;
import dtu.cdio_final.shared.TokenException;
import dtu.cdio_final.shared.dto.UserDTO;

public class UsersServiceTest {

	private DataService service;
	
	@BeforeClass
	public void before(){
		service = new DataServiceImpl();
	}
	
	@Test
	public void test() {
		try {
			service.createUser("", new UserDTO());
		} catch (ServiceException | TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
