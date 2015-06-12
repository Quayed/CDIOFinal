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
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public class MaterialBatchServiceTest {

	private static DataService service;
	private static String token;
	
	@BeforeClass
	public static void before(){
		service = new DataServiceImpl();
		token = TokenHandler.getInstance().createToken(""+1);
	}
	
	@Test
	public void create() {
		try {
			service.createMaterialBatch(token, new MaterialbatchDTO(87, 2, 90.7));
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
		
	@Test
	public void get() {
		try {
			service.getMaterialBatches(token);
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void after(){
		try {
			Connector.doUpdate("DELETE FROM material_batch WHERE mb_id = 87");
		} catch (SQLException e) {}
	}

}
