package dtu.cdio_final.server.dal.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaCompDAO;
import dtu.cdio_final.shared.dto.FormulaCompDTO;

public class FormulaCompDAO implements IFormulaCompDAO {


	@Override
	public FormulaCompDTO getFormulaComp(int formulaID, int materialID) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("SELECT nom_netto, tolerance FROM formula_component WHERE material_id = ? AND formula_id = ?");
			ps.setInt(1, materialID);
			ps.setInt(2, formulaID);
			ResultSet rs = ps.executeQuery();

			if (!rs.first()) {
				return null;
			} else {
				return new FormulaCompDTO(formulaID, materialID, rs.getDouble("nom_netto"),
						rs.getDouble("tolerance"));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<FormulaCompDTO> getFormulaCompList() throws DALException {
		List<FormulaCompDTO> list = new ArrayList<FormulaCompDTO>();
		try {
			ResultSet rs = Connector.doQuery("SELECT formula_id, material_id, nom_netto, tolerance FROM formula_component");
			while (rs.next()) {
				list.add(new FormulaCompDTO(rs.getInt("formula_id"), rs.getInt("material_id"), rs.getDouble("nom_netto"), rs
						.getDouble("tolerance")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public List<FormulaCompDTO> getFormulaCompList(int formulaID) throws DALException {
		List<FormulaCompDTO> list = new ArrayList<FormulaCompDTO>();
		try {
			PreparedStatement ps = Connector.prepare("SELECT material_id, nom_netto, tolerance FROM formula_component WHERE formula_id = ?");
			ps.setInt(1, formulaID);
			ResultSet rs = ps.executeQuery();
			if (!rs.first()) {
				throw new DALException("No formula with ID " + formulaID + " could be found.");
			} else {
				do{
					System.out.println(rs.getInt("material_id"));
					list.add(new FormulaCompDTO(formulaID, rs.getInt("material_id"), rs.getDouble("nom_netto"), rs
							.getDouble("tolerance")));
				}while (rs.next());
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createFormulaComp(FormulaCompDTO formulaComponent) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("INSERT INTO formula_component(formula_id, material_id, nom_netto, tolerance) VALUES (?,?,?,?)");
			ps.setInt(1, formulaComponent.getFormulaID());
			ps.setInt(2, formulaComponent.getMaterialID());
			ps.setDouble(3, formulaComponent.getNomNetto());
			ps.setDouble(4, formulaComponent.getTolerance());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public void updateFormulaComp(FormulaCompDTO formulaComponent) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("UPDATE formula_component SET nom_netto = ?, tolerance = ? WHERE formula_id = ? AND material_id = ?");
			ps.setDouble(1, formulaComponent.getNomNetto());
			ps.setDouble(2, formulaComponent.getTolerance());
			ps.setInt(3, formulaComponent.getFormulaID());
			ps.setInt(4, formulaComponent.getMaterialID());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
