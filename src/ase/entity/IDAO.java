package ase.entity;

import java.io.IOException;

import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public interface IDAO {

	UserDTO getUser(int userID);

	MaterialDTO getMaterialBatch(int materialId);

	void updateMaterial(int getmaterialID, double netto, int userID) throws IOException;

}
