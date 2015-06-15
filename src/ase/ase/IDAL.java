package ase.ase;

import dtu.cdio_final.server.dal.daointerfaces.*;

public interface IDAL {

	IUserDAO getUserDao();

	IMaterialDAO getMaterialDao();

	IFormulaDAO getFormulaDao();

	IFormulaCompDAO getFormulaCompDao();

	IProductbatchDAO getProductBatchDao();

	IProductbatchCompDAO getProductBatchCompDao();

	IMaterialBatchDAO getMaterialBatchDao();

	void connect();

}
