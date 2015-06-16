package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.MaterialBatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialBatchDAO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;

public class TestMaterialBatchDAO {

	private static int insertID = 30;

	private static IMaterialBatchDAO materialbatchDAO;
	private MaterialbatchDTO materialbatchDTO;

	@BeforeClass
	public static void connect() {

		materialbatchDAO = new MaterialBatchDAO();

	}

	@Test
	public void getMaterialbatch() {
		try {
			materialbatchDTO = materialbatchDAO.getMaterialBatch(1);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		assertTrue(materialbatchDTO != null);
	}

	@Test
	public void getMaterialbatchList() {
		try {
			System.out.println("materialBatches: "+ materialbatchDAO.getMaterialBatchList().size());
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void createUpdateMaterialbatch() {
		materialbatchDTO = new MaterialbatchDTO(insertID, 3, 100);
		try {
			materialbatchDAO.createMaterialBatch(materialbatchDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			materialbatchDTO = materialbatchDAO.getMaterialBatch(insertID);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		materialbatchDTO.setQuantity(200);
		try {
			materialbatchDAO.updateMaterialBatch(materialbatchDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertEquals(materialbatchDTO.getQuantity(), materialbatchDAO.getMaterialBatch(insertID).getQuantity(), 0);
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM materialbatch WHERE mb_id = " + insertID);
		} catch (SQLException e) {
		}
	}

}
