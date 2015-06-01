package dtu.cdio_final.server.dal.daointerfaces;

import java.util.List;

import dtu.cdio_final.shared.dto.MaterialbatchDTO;

public interface IMaterialBatchDAO {
	MaterialbatchDTO getMaterialBatch(int mbID) throws DALException;

	List<MaterialbatchDTO> getMaterialBatchList() throws DALException;

	List<MaterialbatchDTO> getMaterialBatchList(int materialID) throws DALException;

	void createMaterialBatch(MaterialbatchDTO materialbatch) throws DALException;

	void updateMaterialBatch(MaterialbatchDTO materialbatch) throws DALException;
}
