package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.ProductbatchCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchCompDAO;
import dtu.cdio_final.shared.dto.ProductbatchCompDTO;

public class TestProductbatchCompDAO {

	private static int pbID = 1;
	private static int mbID = 3;

	private static IProductbatchCompDAO productbatchCompDAO;
	private ProductbatchCompDTO productbatchCompDTO;

	@BeforeClass
	public static void connect() {

		productbatchCompDAO = new ProductbatchCompDAO();

	}

	@Test
	public void getProductbatchComp() {
		try {
			productbatchCompDTO = productbatchCompDAO.getProductbatchComp(1, 1);
			assertNotNull(productbatchCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getProductbatchCompList() throws DALException {
		try {
			productbatchCompDAO.getProductbatchCompList();
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getProductbatchCompList2() throws DALException {
		try {
			productbatchCompDAO.getProductbatchCompList(3);
		} catch (DALException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void createUpdateProductbatchComp() {

		productbatchCompDTO = new ProductbatchCompDTO(pbID, mbID, 2, 0.5, 3.65);

		try {

			productbatchCompDAO.createProductbatchComp(productbatchCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		productbatchCompDTO.setNetto(2.76);
		try {
			productbatchCompDAO.updateProductbatchComp(productbatchCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		try {
			assertEquals(productbatchCompDTO.getNetto(), productbatchCompDAO.getProductbatchComp(pbID, mbID).getNetto(), 0);
		} catch (DALException e) {
			fail(e.getMessage());
		}

	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM productbatch_component WHERE pb_id = " + pbID + " AND mb_id = " + mbID);
		} catch (SQLException e) {
		}
	}
}
