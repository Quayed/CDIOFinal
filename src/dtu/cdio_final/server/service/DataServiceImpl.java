package dtu.cdio_final.server.service;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.server.dal.daoimpl.FormulaCompDAO;
import dtu.cdio_final.server.dal.daoimpl.FormulaDAO;
import dtu.cdio_final.server.dal.daoimpl.MaterialBatchDAO;
import dtu.cdio_final.server.dal.daoimpl.MaterialDAO;
import dtu.cdio_final.server.dal.daoimpl.ProductbatchDAO;
import dtu.cdio_final.server.dal.daoimpl.UserDAO;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialBatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialDAO;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IUserDAO;
import dtu.cdio_final.shared.FieldVerifier;
import dtu.cdio_final.shared.ServiceException;
import dtu.cdio_final.shared.TokenException;
import dtu.cdio_final.shared.TokenHandler;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements DataService
{
	private final IUserDAO userDao = new UserDAO();
	private final IMaterialDAO materialDao = new MaterialDAO();
	private final IFormulaDAO formulaDao = new FormulaDAO();
	private final IFormulaCompDAO formulaCompDao = new FormulaCompDAO();
	private final IMaterialBatchDAO materialBatchDao = new MaterialBatchDAO();
	private final IProductbatchDAO productBathcDao = new ProductbatchDAO();

	@Override
	public void createUser(String token, UserDTO user) throws ServiceException, TokenException
	{
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+user.getUserID()) &&
			FieldVerifier.isValidCPR(user.getCpr()) &&
			FieldVerifier.isValidName(user.getUserName()) &&
			FieldVerifier.isValidInitials(user.getIni()) &&
			FieldVerifier.isValidPassword(user.getPassword()) &&
			FieldVerifier.isValidRole(user.getRole())
		);
		
		user.setStatus(1);
		
		try
		{
			if(userDao.getUser(user.getUserID()) != null){
				throw new ServiceException("UserID already in use");
			}
			userDao.createUser(user);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<UserDTO> getUsers(String token) throws ServiceException, TokenException
	{
		validateToken(token);
			
		List<UserDTO> users; 
		try
		{
			users = userDao.getUserList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return users;
	}

	@Override
	public void updateUser(String token, UserDTO user) throws ServiceException, TokenException
	{
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+user.getUserID()) &&
			FieldVerifier.isValidCPR(user.getCpr()) &&
			FieldVerifier.isValidName(user.getUserName()) &&
			FieldVerifier.isValidInitials(user.getIni()) &&
			FieldVerifier.isValidPassword(user.getPassword()) &&
			FieldVerifier.isValidRole(user.getRole()) &&
			FieldVerifier.isValidUserStatus(user.getStatus())
		);
		try
		{
			userDao.updateUser(user);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public void createMaterial(String token, MaterialDTO material) throws ServiceException, TokenException
	{
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+material.getMaterialID()) &&
			FieldVerifier.isValidName(material.getMaterialName()) &&
			FieldVerifier.isValidName(material.getProvider())
		);
		
		try
		{
			if(materialDao.getMaterial(material.getMaterialID()) != null){
				throw new ServiceException("MaterialID already exsists");
			}
			materialDao.createMaterial(material);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<MaterialDTO> getMaterials(String token) throws ServiceException, TokenException
	{
		validateToken(token);
		
		List<MaterialDTO> result; 
		try
		{
			result = materialDao.getMaterialList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void createFormualWithComponents(String token, FormulaDTO formula, List<FormulaCompDTO> components) throws ServiceException, TokenException {
		
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+formula.getFormulaID()) &&
			FieldVerifier.isValidName(formula.getFormulaName()) &&
			components != null
		);
		
		
		try {
			if(formulaDao.getFormula(formula.getFormulaID()) != null){
				throw new ServiceException("FormulaID already exsists");
			}
			formulaDao.createFormula(formula);
		} catch (DALException e) {
			throw new ServiceException(e);
		}	
		for(FormulaCompDTO component : components){
			try {
				if(materialDao.getMaterial(component.getMaterialID()) == null){
					throw new ServiceException("Material don't exist.");
				}
				formulaCompDao.createFormulaComp(component);
			} catch (DALException e) {
				throw new ServiceException(e);
			}
		}
	}
	
	@Override
	public List<FormulaDTO> getFormulas(String token) throws ServiceException, TokenException
	{
		validateToken(token);
		
		List<FormulaDTO> formulas; 
		try
		{
			formulas = formulaDao.getFormulaList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return formulas;
	}
	
	@Override
	public List<FormulaCompDTO> getFormulaComps(String token, int formulaID) throws ServiceException, TokenException
	{
		validateToken(token);
		
		
		List<FormulaCompDTO> formulaComps; 
		try
		{
			formulaComps = formulaCompDao.getFormulaCompList(formulaID);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return formulaComps;
	}

	@Override
	public void createMaterialBatch(String token, MaterialbatchDTO materialBatch) throws ServiceException, TokenException
	{
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+materialBatch.getMbID()) &&
			FieldVerifier.isValidID(""+materialBatch.getMaterialID()) &&
			FieldVerifier.isValidQuantity(Double.toString(materialBatch.getQuantity()))
		);
		
		try
		{
			if(materialBatchDao.getMaterialBatch(materialBatch.getMbID()) != null){
				throw new ServiceException("MaterialBatchID already exsists");
			}
			if(materialDao.getMaterial(materialBatch.getMaterialID()) == null){
				throw new ServiceException("MaterialID don't exsists");
			}
			materialBatchDao.createMaterialBatch(materialBatch);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatches(String token) throws ServiceException, TokenException
	{
		
		validateToken(token);
		
		List<MaterialbatchDTO> materialBatches;
		try
		{
			materialBatches = materialBatchDao.getMaterialBatchList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return materialBatches;
	}

	@Override
	public void createProductBatch(String token, ProductbatchDTO productBatch) throws ServiceException, TokenException
	{
		
		validateToken(token);
		
		invalidArguments(
			FieldVerifier.isValidID(""+productBatch.getPbID()) &&
			FieldVerifier.isValidID(""+productBatch.getFormulaID())
		);
		
		try
		{
			if(productBathcDao.getProductbatch(productBatch.getPbID()) != null){
				throw new ServiceException("ProductBatchID already exsists");
			}
			if(formulaDao.getFormula(productBatch.getFormulaID()) == null){
				throw new ServiceException("MaterialID don't exsists");
			}
			productBatch.setStatus(1);
			productBathcDao.createProductbatch(productBatch);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProductbatchDTO> getProductBatches(String token) throws ServiceException, TokenException
	{
		validateToken(token);
		
		List<ProductbatchDTO> productBatches; 
		try
		{
			productBatches = productBathcDao.getProductbatchList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return productBatches;
	}

	@Override
	public HashMap<String, Object> login(int userID, String password) throws ServiceException
	{
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		try
		{
			UserDTO user = userDao.getUser(userID);
			
			if((user != null) && (user.getPassword().equals(password)) && (user.getStatus() != 0))
			{
				results.put("token", TokenHandler.getInstance().createToken(Integer.toString(userID)));
				results.put("user", user);
				
				return results;
			}
				
		}
		catch (DALException e)
		{
			System.err.println("Invalid login or password");
			throw new ServiceException(e);
		}
		return null;
	}
	
	private void validateToken(String token) throws TokenException
	{
		//override
		token = TokenHandler.getInstance().validateToken(token);
		if(token == null)
			throw new TokenException();
	}
	
	private void invalidArguments(boolean validation) throws ServiceException{
		if(!validation){
			throw new ServiceException("Invalid Arguments");
		}
	}
}
