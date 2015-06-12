package dtu.cdio_final.client.service;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import dtu.cdio_final.shared.ServiceException;
import dtu.cdio_final.shared.TokenException;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public interface DataService extends RemoteService{
	void createUser(String token, UserDTO user) throws ServiceException, TokenException;
	List<UserDTO> getUsers(String token) throws ServiceException, TokenException;
	void updateUser(String token, UserDTO user) throws ServiceException, TokenException;
	
	void createMaterial(String token, MaterialDTO material) throws ServiceException, TokenException;
	List<MaterialDTO> getMaterials(String token) throws ServiceException, TokenException;
	
	void createFormualWithComponents(String token, FormulaDTO formula, List<FormulaCompDTO> components) throws ServiceException, TokenException;
	List<FormulaDTO> getFormulas(String token) throws ServiceException, TokenException;
	List<FormulaCompDTO> getFormulaComps(String token, int formulaID) throws ServiceException, TokenException;

	void createMaterialBatch(String token, MaterialbatchDTO materialBatch) throws ServiceException, TokenException;
	List<MaterialbatchDTO> getMaterialBatches(String token) throws ServiceException, TokenException;
	
	void createProductBatch(String token, ProductbatchDTO productBatch) throws ServiceException, TokenException;
	List<ProductbatchDTO> getProductBatches(String token) throws ServiceException, TokenException;
	
	HashMap<String, Object> login(int userID, String password) throws ServiceException, TokenException;
}
