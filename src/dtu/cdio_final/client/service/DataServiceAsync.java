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
	void createUser(UserDTO user, AsyncCallback<Void> callback);
	void getUsers(String token, AsyncCallback<List<UserDTO>> callback);
	void updateUser(UserDTO user, AsyncCallback<Void> callback);
	void deleteUser(int userID, AsyncCallback<Void> callback);
	
	void createMaterial(MaterialDTO material, AsyncCallback<Void> callback);
	void getMaterials(AsyncCallback<List<MaterialDTO>> callback);
	void updateMaterial(MaterialDTO material, AsyncCallback<Void> callback);
	
	void createFormula(FormulaDTO formula, AsyncCallback<Void> callback);
	void getFormulas(AsyncCallback<List<FormulaDTO>> callback);
	void createFormualWithComponents(FormulaDTO formula, List<FormulaCompDTO> components, AsyncCallback<Void> callback);
	
	void createFormulaComp(FormulaCompDTO formulaComp, AsyncCallback<Void> callback);
	void getFormulaComps(int formulaID,AsyncCallback<List<FormulaCompDTO>> callback);
	
	void createMaterialBatch(MaterialbatchDTO materialBatch, AsyncCallback<Void> callback);
	void getMaterialBatches(AsyncCallback<List<MaterialbatchDTO>> callback);
	void updateMaterialBatch(MaterialbatchDTO materialBatch, AsyncCallback<Void> asyncCallback);
	
	void createProductBatch(ProductbatchDTO productBatch, AsyncCallback<Void> callback);
	void getProductBatches(AsyncCallback<List<ProductbatchDTO>> callback);
	
	void login(int userID, String password, AsyncCallback<HashMap<String, Object>> callback);
}
