package dtu.cdio_final.server.dal.daointerfaces;

import java.util.List;

import dtu.cdio_final.shared.dto.FormulaCompDTO;

public interface IFormulaCompDAO {
	FormulaCompDTO getFormulaComp(int formulaID, int materialID) throws DALException;

	List<FormulaCompDTO> getFormulaCompList() throws DALException;

	List<FormulaCompDTO> getFormulaCompList(int formulaID) throws DALException;

	void createFormulaComp(FormulaCompDTO formulaComponent) throws DALException;

	void updateFormulaComp(FormulaCompDTO formulaComponent) throws DALException;
}
