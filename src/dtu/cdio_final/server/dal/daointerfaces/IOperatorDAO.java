package dtu.cdio_final.server.dal.daointerfaces;

import java.util.List;

import dtu.cdio_final.shared.dto.OperatorDTO;

public interface IOperatorDAO {
	OperatorDTO getOperator(int oprId) throws DALException;

	List<OperatorDTO> getOperatorList() throws DALException;

	void createOperator(OperatorDTO operator) throws DALException;

	void updateOperator(OperatorDTO operator) throws DALException;
}
