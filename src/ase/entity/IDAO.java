package ase.entity;

import java.io.IOException;

import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.OperatorDTO;

public interface IDAO {

	OperatorDTO getOperator(int oprID);

	MaterialDTO getMaterialBatch(int materialId);

	void updateMaterial(int getmaterialID, double netto, int oprID) throws IOException;

}
