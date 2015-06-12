package dtu.cdio_final.client.service;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public interface DataServiceAsync {
	void createUser(String token, UserDTO user, AsyncCallback<Void> callback);
	void getUsers(String token, AsyncCallback<List<UserDTO>> callback);
	void updateUser(String token, UserDTO user, AsyncCallback<Void> callback);
	
	void createMaterial(String token, MaterialDTO material, AsyncCallback<Void> callback);
	void getMaterials(String token, AsyncCallback<List<MaterialDTO>> callback);
	
	void createFormualWithComponents(String token, FormulaDTO formula, List<FormulaCompDTO> components, AsyncCallback<Void> callback);
	void getFormulas(String token, AsyncCallback<List<FormulaDTO>> callback);
	void getFormulaComps(String token, int formulaID,AsyncCallback<List<FormulaCompDTO>> callback);
	
	void createMaterialBatch(String token, MaterialbatchDTO materialBatch, AsyncCallback<Void> callback);
	void getMaterialBatches(String token, AsyncCallback<List<MaterialbatchDTO>> callback);
	
	void createProductBatch(String token, ProductbatchDTO productBatch, AsyncCallback<Void> callback);
	void getProductBatches(String token, AsyncCallback<List<ProductbatchDTO>> callback);
	
	void login(int userID, String password, AsyncCallback<HashMap<String, Object>> callback);

}
