package ase.ase;

import ase.shared.SocketHandler;
import dtu.cdio_final.server.dal.daoimpl.*;
import dtu.cdio_final.server.dal.daointerfaces.*;

public class Logic implements ILogic {
	private IUserDAO userDao;
	private IMaterialDAO materialDao;
	private IFormulaDAO formulaDao;
	private IFormulaCompDAO formulaCompDao;
	private IMaterialBatchDAO materialBatchDao;
	private IProductbatchDAO productBathcDao;
	private SocketHandler socketHandler;

	public Logic() {
		userDao = new UserDAO();
		materialDao = new MaterialDAO();
		formulaDao = new FormulaDAO();
		formulaCompDao = new FormulaCompDAO();
		materialBatchDao = new MaterialBatchDAO();
		productBathcDao = new ProductbatchDAO();
	}

	public Logic(IUserDAO userDao, IMaterialDAO materialDao, IFormulaDAO formulaDao, IFormulaCompDAO formulaCompDao, IMaterialBatchDAO materialBatchDao, IProductbatchDAO productBathcDao) {
		this.userDao = userDao;
		this.materialDao = materialDao;
		this.formulaDao = formulaDao;
		this.formulaCompDao = formulaCompDao;
		this.materialBatchDao = materialBatchDao;
		this.productBathcDao = productBathcDao;
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
	public IProductbatchDAO getProductBathcDao() {
		return productBathcDao;
	}

	@Override
	public IMaterialBatchDAO getMaterialBatchDao() {
		return materialBatchDao;
	}

	@Override
	public void connect() {
		socketHandler.
	}
}
