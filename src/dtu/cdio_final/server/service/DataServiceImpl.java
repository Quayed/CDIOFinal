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
	public void createMaterial(MaterialDTO material) throws ServiceException, TokenException
	{
		try
		{
			materialDao.createMaterial(material);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<MaterialDTO> getMaterials() throws ServiceException
	{
		List<MaterialDTO> result = null; 
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
	public void updateMaterial(MaterialDTO material) throws ServiceException
	{
		try
		{
			materialDao.updateMaterial(material);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public void createFormula(FormulaDTO formula) throws ServiceException
	{
		try
		{
			formulaDao.createFormula(formula);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<FormulaDTO> getFormulas() throws ServiceException
	{
		List<FormulaDTO> result = null; 
		try
		{
			result = formulaDao.getFormulaList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void createFormulaComp(FormulaCompDTO formulaComp) throws ServiceException
	{
		try
		{
			formulaCompDao.createFormulaComp(formulaComp);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<FormulaCompDTO> getFormulaComps(int formulaID) throws ServiceException
	{
		List<FormulaCompDTO> result = null; 
		try
		{
			result = formulaCompDao.getFormulaCompList(formulaID);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void createMaterialBatch(MaterialbatchDTO materialBatch) throws ServiceException
	{
		try
		{
			materialBatchDao.createMaterialBatch(materialBatch);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatches() throws ServiceException
	{
		List<MaterialbatchDTO> result = null; 
		try
		{
			result = materialBatchDao.getMaterialBatchList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void createProductBatch(ProductbatchDTO productBatch) throws ServiceException
	{
		try
		{
			productBathcDao.createProductbatch(productBatch);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProductbatchDTO> getProductBatches() throws ServiceException
	{
		List<ProductbatchDTO> result = null; 
		try
		{
			result = productBathcDao.getProductbatchList();
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public HashMap<String, Object> login(int userID, String password) throws ServiceException
	{
		//TODO: update to use tokens
		//TODO: update to use hashing
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		try
		{
			UserDTO user = userDao.getUser(userID);
			
			if((user != null) && user.getPassword().equals(password))
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
//		token = TokenHandler.getInstance().validateToken(token);
//		return !(token == null);
//		if(token == null)
//			throw new TokenException();
	}

	@Override
	public void updateMaterialBatch(MaterialbatchDTO materialBatch) throws ServiceException {
		
		try
		{
			materialBatchDao.updateMaterialBatch(materialBatch);
		}
		catch (DALException e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public void createFormualWithComponents(FormulaDTO formula, List<FormulaCompDTO> components) throws ServiceException {
		try {
			formulaDao.createFormula(formula);
		} catch (DALException e) {
			throw new ServiceException(e);
		}	
		for(FormulaCompDTO component : components){
			try {
				formulaCompDao.createFormulaComp(component);
			} catch (DALException e) {
				throw new ServiceException(e);
			}
		}
	}
	
	private void invalidArguments(boolean validation) throws ServiceException{
		if(!validation){
			throw new ServiceException("Invalid Arguments");
		}
	}
}
