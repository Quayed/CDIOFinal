package test.service;

import static org.junit.Assert.fail;

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
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class ProductBatchServiceTest {

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
			service.createProductBatch(token, new ProductbatchDTO(87, 2, 4));
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
		
	@Test
	public void get() {
		try {
			service.getProductBatches(token);
		} catch (ServiceException | TokenException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void after(){
		try {
			Connector.doUpdate("DELETE FROM productbatch WHERE pb_id = 87");
		} catch (SQLException e) {}
	}

}
