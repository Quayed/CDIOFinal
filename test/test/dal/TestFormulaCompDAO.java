package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.FormulaCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaCompDAO;
import dtu.cdio_final.shared.dto.FormulaCompDTO;

public class TestFormulaCompDAO {

	private static int materialID = 7;
	private static int formulaID = 1;

	private static IFormulaCompDAO formulaCompDAO;
	private FormulaCompDTO formulaCompDTO;

	@BeforeClass
	public static void connect() {

		formulaCompDAO = new FormulaCompDAO();

	}

	@Test
	public void getFormulaComp() {
		try {
			formulaCompDTO = formulaCompDAO.getFormulaComp(1, 1);
			assertNotNull(formulaCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getFormulaCompList() throws DALException {
		try {
			for (FormulaCompDTO fc : formulaCompDAO.getFormulaCompList()) {
				System.out.println(fc);
			}
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getFormulaCompList2() throws DALException {
		try {
			formulaCompDAO.getFormulaCompList(3);
		} catch (DALException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void createUpdateFormulaComp() {

		formulaCompDTO = new FormulaCompDTO(formulaID, materialID, 2.1, 0.1);

		try {

			formulaCompDAO.createFormulaComp(formulaCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		formulaCompDTO.setNomNetto(1.7);
		try {
			formulaCompDAO.updateFormulaComp(formulaCompDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		try {
			assertEquals(formulaCompDTO.getNomNetto(), formulaCompDAO.getFormulaComp(formulaID, materialID).getNomNetto(), 0);
		} catch (DALException e) {
			fail(e.getMessage());
		}

	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM formula_component WHERE formula_id = " + formulaID + " AND material_id = "
					+ materialID);
		} catch (SQLException e) {
		}
	}
}
