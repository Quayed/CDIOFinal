package ase.ase;

import dtu.cdio_final.server.dal.daointerfaces.IFormulaCompDAO;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialBatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialDAO;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchDAO;
import dtu.cdio_final.server.dal.daointerfaces.IUserDAO;

public interface IDAL {

	IUserDAO getUserDao();

	IMaterialDAO getMaterialDao();

	IFormulaDAO getFormulaDao();

	IFormulaCompDAO getFormulaCompDao();

	IProductbatchDAO getProductBathcDao();

	IMaterialBatchDAO getMaterialBatchDao();

	void connect();

}
