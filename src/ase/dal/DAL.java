package ase.dal;

import dtu.cdio_final.server.dal.daoimpl.FormulaCompDAO;
import dtu.cdio_final.server.dal.daoimpl.FormulaDAO;
import dtu.cdio_final.server.dal.daoimpl.MaterialBatchDAO;
import dtu.cdio_final.server.dal.daoimpl.MaterialDAO;
import dtu.cdio_final.server.dal.daoimpl.ProductbatchCompDAO;
import dtu.cdio_final.server.dal.daoimpl.ProductbatchDAO;
import dtu.cdio_final.server.dal.daoimpl.UserDAO;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialBatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialDAO;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IUserDAO;

public class DAL implements IDAL {
	private IUserDAO userDao;
	private IMaterialDAO materialDao;
	private IFormulaDAO formulaDao;
	private IFormulaCompDAO formulaCompDao;
	private IMaterialBatchDAO materialBatchDao;
	private IProductbatchDAO productBatchDao;
	private IProductbatchCompDAO productBatchCompDao;

	public DAL() {
		userDao = new UserDAO();
		materialDao = new MaterialDAO();
		materialBatchDao = new MaterialBatchDAO();
		formulaDao = new FormulaDAO();
		formulaCompDao = new FormulaCompDAO();
		productBatchDao = new ProductbatchDAO();
		productBatchCompDao = new ProductbatchCompDAO();
	}

	public DAL(IUserDAO userDao, IMaterialDAO materialDao, IFormulaDAO formulaDao, IFormulaCompDAO formulaCompDao, IMaterialBatchDAO materialBatchDao, IProductbatchDAO productBathcDao,
			IProductbatchCompDAO productBatchCompDao) {
		this.userDao = userDao;
		this.materialDao = materialDao;
		this.formulaDao = formulaDao;
		this.formulaCompDao = formulaCompDao;
		this.materialBatchDao = materialBatchDao;
		this.productBatchDao = productBathcDao;
		this.productBatchCompDao = productBatchCompDao;
	}

	@Override
	public IUserDAO getUserDao() {
		return userDao;
	}

	@Override
	public IMaterialDAO getMaterialDao() {
		return materialDao;
	}

	@Override
	public IFormulaDAO getFormulaDao() {
		return formulaDao;
	}

	@Override
	public IFormulaCompDAO getFormulaCompDao() {
		return formulaCompDao;
	}

	@Override
	public IProductbatchDAO getProductBatchDao() {
		return productBatchDao;
	}

	@Override
	public IProductbatchCompDAO getProductBatchCompDao() {
		return productBatchCompDao;
	}

	@Override
	public IMaterialBatchDAO getMaterialBatchDao() {
		return materialBatchDao;
	}

	@Override
	public void connect() {
	}

}
