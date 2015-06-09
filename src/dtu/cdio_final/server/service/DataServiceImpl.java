package dtu.cdio_final.server.service;

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
	public void createUser(UserDTO user)
	{
		try
		{
			userDao.createUser(user);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<UserDTO> getUsers()
	{
		List<UserDTO> result = null; 
		try
		{
			result = userDao.getUserList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateUser(UserDTO user)
	{
		try
		{
			userDao.updateUser(user);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(int userID)
	{	
		try
		{
			UserDTO user = userDao.getUser(userID);
			user.setStatus(0);
			userDao.updateUser(user);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createMaterial(MaterialDTO material)
	{
		try
		{
			materialDao.createMaterial(material);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<MaterialDTO> getMaterials()
	{
		List<MaterialDTO> result = null; 
		try
		{
			result = materialDao.getMaterialList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateMaterial(MaterialDTO material)
	{
		try
		{
			materialDao.updateMaterial(material);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createFormula(FormulaDTO formula)
	{
		try
		{
			formulaDao.createFormula(formula);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<FormulaDTO> getFormulas()
	{
		List<FormulaDTO> result = null; 
		try
		{
			result = formulaDao.getFormulaList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void createFormulaComp(FormulaCompDTO formulaComp)
	{
		try
		{
			formulaCompDao.createFormulaComp(formulaComp);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<FormulaCompDTO> getFormulaComps(int formulaID)
	{
		List<FormulaCompDTO> result = null; 
		try
		{
			result = formulaCompDao.getFormulaCompList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void createMaterialBatch(MaterialbatchDTO materialBatch)
	{
		try
		{
			materialBatchDao.createMaterialBatch(materialBatch);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatches()
	{
		List<MaterialbatchDTO> result = null; 
		try
		{
			result = materialBatchDao.getMaterialBatchList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void createProductBatch(ProductbatchDTO productBatch)
	{
		try
		{
			productBathcDao.createProductbatch(productBatch);
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<ProductbatchDTO> getProductBatches()
	{
		List<ProductbatchDTO> result = null; 
		try
		{
			result = productBathcDao.getProductbatchList();
		}
		catch (DALException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String login(int userID, String password)
	{
		//TODO: update to use tokens
		//TODO: update to use hashing
		try
		{
			UserDTO user = userDao.getUser(userID);
			
			if((user != null) && user.getPassword().equals(password))
				return TokenHandler.getInstance().createToken(Integer.toString(userID));
		}
		catch (DALException e)
		{
			System.err.println("Invalid login or password");
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean validateToken(String token)
	{
		token = TokenHandler.getInstance().validateToken(token);
		return token == null;
	}
}
