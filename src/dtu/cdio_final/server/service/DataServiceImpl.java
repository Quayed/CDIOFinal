package dtu.cdio_final.server.service;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public class DataServiceImpl extends RemoteServiceServlet implements DataService{

	@Override
	public void createUser(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserDTO> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(int userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createMaterial(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MaterialDTO> getMaterials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMaterial(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createFormula(FormulaDTO formula) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FormulaDTO> getFormulas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createFormulaComp(FormulaCompDTO formulaComp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FormulaCompDTO> getFormulaComps(int formulaID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMaterialBatch(MaterialbatchDTO materialBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProductBatch(ProductbatchDTO productBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductbatchDTO> getProductBatches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(int userID, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
