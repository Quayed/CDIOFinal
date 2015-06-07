package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.ProductbatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchDAO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class TestProductbatchDAO {

	private final static int insertID = 76;

	private static IProductbatchDAO productbatchDAO;
	private ProductbatchDTO productbatchDTO;

	@BeforeClass
	public static void connect() {

		productbatchDAO = new ProductbatchDAO();

	}

	@Test
	public void getProductbatch() {
		try {
			productbatchDTO = productbatchDAO.getProductbatch(1);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		assertTrue(productbatchDTO != null);
	}

	@Test
	public void getProductbatchList() {
		try {
			productbatchDAO.getProductbatchList();
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void createUpdateProductbatch() {
		productbatchDTO = new ProductbatchDTO(insertID, 2, 0);
		try {
			productbatchDAO.createProductbatch(productbatchDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			productbatchDTO = productbatchDAO.getProductbatch(insertID);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		productbatchDTO.setStatus(1);
		try {
			productbatchDAO.updateProductbatch(productbatchDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertEquals(productbatchDTO.getStatus(), productbatchDAO.getProductbatch(insertID).getStatus());
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM productbatch WHERE pb_id = " + insertID);
		} catch (SQLException e) {
		}
	}

}
