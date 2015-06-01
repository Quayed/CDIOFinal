package dtu.cdio_final.server.dal.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;
import dtu.cdio_final.shared.dto.FormulaDTO;

public class FormulaDAO implements IFormulaDAO {

	
	@Override
	public FormulaDTO getFormula(int formulaID) throws DALException {
		try{
			PreparedStatement ps = Connector.prepare("SELECT formula_name FROM formula WHERE formula_id = ?");
			ps.setInt(1, formulaID);
			ResultSet rs = ps.executeQuery();
			if(!rs.first()){
				return null;
			} else {
				return new FormulaDTO(formulaID, rs.getString("formula_name"));
			}
		} catch (SQLException e){
			throw new DALException(e);
		}
	}

	@Override
	public List<FormulaDTO> getFormulaList() throws DALException {
		List<FormulaDTO> list = new ArrayList<FormulaDTO>();
		try {
			ResultSet rs = Connector.doQuery("SELECT formula_id, formula_name FROM formula");
			while (rs.next()) {
				list.add(new FormulaDTO(rs.getInt("formula_id"), rs.getString("formula_name")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createFormula(FormulaDTO formula) throws DALException {
		try{
			PreparedStatement ps = Connector.prepare("INSERT INTO formula(formula_id, formula_name) VALUES (null, ?);");
			ps.setString(1, formula.getFormulaName());
			ps.execute();
			formula.setFormulaID(Connector.getLastInsert(ps));
		} catch(SQLException e){
			throw new DALException(e);
		}
	}

	@Override
	public void updateFormula(FormulaDTO formula) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("UPDATE formula SET formula_name = ? WHERE formula_id = ?");
			ps.setString(1, formula.getFormulaName());
			ps.setInt(2, formula.getFormulaID());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
