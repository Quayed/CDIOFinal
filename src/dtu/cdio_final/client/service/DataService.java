package dtu.cdio_final.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public interface DataService extends RemoteService{
	void createUser(UserDTO user);
	List<UserDTO> getUsers();
	void updateUser(UserDTO user);
	void deleteUser(int userID);
	
	void createMaterial(MaterialDTO material);
	List<MaterialDTO> getMaterials();
	void updateMaterial(MaterialDTO material);
	
	void createFormula(FormulaDTO formula);
	List<FormulaDTO> getFormulas();
	
	void createFormulaComp(FormulaCompDTO formulaComp);
	List<FormulaCompDTO> getFormulaComps(int formulaID);
	
	void createMaterialBatch(MaterialbatchDTO materialBatch);
	List<MaterialbatchDTO> getMaterialBatches();
	
	void createProductBatch(ProductbatchDTO productBatch);
	List<ProductbatchDTO> getProductBatches();
	
	String login(int userID, String password);
	
}
