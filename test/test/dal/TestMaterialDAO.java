package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.MaterialDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialDAO;
import dtu.cdio_final.shared.dto.MaterialDTO;

public class TestMaterialDAO {

	private static int insertID;

	private static IMaterialDAO materialDAO;
	private MaterialDTO materialDTO;

	@BeforeClass
	public static void connect() {

		materialDAO = new MaterialDAO();

	}

	@Test
	public void getMaterial() {
		try {
			materialDTO = materialDAO.getMaterial(1);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		assertTrue(materialDTO != null);
	}

	@Test
	public void getMaterialList() {
		try {
			materialDAO.getMaterialList();
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void createUpdateMaterial() {
		materialDTO = new MaterialDTO(0, "Origano", "OrIgana A/S");
		try {
			materialDAO.createMaterial(materialDTO);
			insertID = materialDTO.getMaterialID();
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			materialDTO = materialDAO.getMaterial(insertID);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		materialDTO.setMaterialName("Persille");
		try {
			materialDAO.updateMaterial(materialDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertEquals(materialDTO.getMaterialName(), materialDAO.getMaterial(insertID).getMaterialName());
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM material WHERE material_id = " + insertID);
		} catch (SQLException e) {
		}
	}

}
