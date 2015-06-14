package ase.ase;

import dtu.cdio_final.server.dal.daoimpl.*;
import dtu.cdio_final.server.dal.daointerfaces.*;
import dtu.cdio_final.server.dal.connector.*;

public class DAL implements IDAL {
	private IUserDAO userDao;
	private IMaterialDAO materialDao;
	private IFormulaDAO formulaDao;
	private IFormulaCompDAO formulaCompDao;
	private IMaterialBatchDAO materialBatchDao;
	private IProductbatchDAO productBathcDao;

	public DAL() {
		userDao = new UserDAO();
		materialDao = new MaterialDAO();
		formulaDao = new FormulaDAO();
		formulaCompDao = new FormulaCompDAO();
		materialBatchDao = new MaterialBatchDAO();
		productBathcDao = new ProductbatchDAO();
	}

	public DAL(IUserDAO userDao, IMaterialDAO materialDao, IFormulaDAO formulaDao, IFormulaCompDAO formulaCompDao, IMaterialBatchDAO materialBatchDao, IProductbatchDAO productBathcDao) {
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
		Connector.connect(server, port, database, username, password);
	}

}
