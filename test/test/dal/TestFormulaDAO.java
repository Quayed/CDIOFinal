package test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daoimpl.FormulaDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;
import dtu.cdio_final.shared.dto.FormulaDTO;

public class TestFormulaDAO {

	private static int insertID;

	private static IFormulaDAO formulaDAO;
	private FormulaDTO formulaDTO;

	@BeforeClass
	public static void connect() {
		
		formulaDAO = new FormulaDAO();

	}

	@Test
	public void getFormula() {
		try {
			formulaDTO = formulaDAO.getFormula(1);
		} catch (DALException e) {
			fail(e.getMessage());
		}
		assertTrue(formulaDTO != null);
	}

	@Test
	public void getFormulaList() {
		try {
			formulaDAO.getFormulaList();
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void createUpdateFormula() {
		formulaDTO = new FormulaDTO(0, "Test");
		try {
			formulaDAO.createFormula(formulaDTO);
			insertID = formulaDTO.getFormulaID();
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			formulaDTO = formulaDAO.getFormula(insertID);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		formulaDTO.setFormulaName("Bahamas");
		try {
			formulaDAO.updateFormula(formulaDTO);
		} catch (DALException e) {
			fail(e.getMessage());
		}

		try {
			assertEquals(formulaDTO.getFormulaName(), formulaDAO.getFormula(insertID).getFormulaName());
		} catch (DALException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void close() {
		try {
			Connector.doUpdate("DELETE FROM formula WHERE formula_id = " + insertID);
		} catch (SQLException e) {
		}
	}
}
