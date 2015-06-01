package dtu.cdio_final.server.dal.daointerfaces;

import java.util.List;

import dtu.cdio_final.shared.dto.FormulaDTO;

public interface IFormulaDAO {
	FormulaDTO getFormula(int formulaID) throws DALException;

	List<FormulaDTO> getFormulaList() throws DALException;

	void createFormula(FormulaDTO formula) throws DALException;

	void updateFormula(FormulaDTO formula) throws DALException;
}
