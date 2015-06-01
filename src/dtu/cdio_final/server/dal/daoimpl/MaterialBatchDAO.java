package dtu.cdio_final.server.dal.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IMaterialBatchDAO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;

public class MaterialBatchDAO implements IMaterialBatchDAO {

	
	@Override
	public MaterialbatchDTO getMaterialBatch(int mbID) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("SELECT material_id, quantity FROM materialbatch WHERE mb_id = ?");
			ps.setInt(1, mbID);
			ResultSet rs = ps.executeQuery();
			if (!rs.first()) {
				return null;
			} else {
				return new MaterialbatchDTO(mbID, rs.getInt("material_id"), rs.getDouble("quantity"));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatchList() throws DALException {
		ArrayList<MaterialbatchDTO> list = new ArrayList<MaterialbatchDTO>();
		try {
			ResultSet rs = Connector.doQuery("SELECT mb_id, material_id, quantity FROM materialbatch");
			while (rs.next()) {
				list.add(new MaterialbatchDTO(rs.getInt("mb_id"), rs.getInt("material_id"), rs.getDouble("quantity")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public List<MaterialbatchDTO> getMaterialBatchList(int materialID) throws DALException {
		ArrayList<MaterialbatchDTO> list = new ArrayList<MaterialbatchDTO>();
		try {
			PreparedStatement ps = Connector.prepare("SELECT mb_id, quantity FROM materialbatch WHERE material_id = ?");
			ps.setInt(1, materialID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new MaterialbatchDTO(rs.getInt("mb_id"), materialID, rs.getDouble("quantity")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createMaterialBatch(MaterialbatchDTO materialbatch) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("INSERT INTO materialbatch (material_id, quantity) VALUES (?,?)");
			ps.setInt(1, materialbatch.getMaterialID());
			ps.setDouble(2, materialbatch.getQuantity());
			ps.execute();
			materialbatch.setMaterialID(Connector.getLastInsert(ps));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public void updateMaterialBatch(MaterialbatchDTO materialbatch) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("UPDATE materialbatch SET material_id = ?, quantity = ? WHERE mb_id = ?");
			ps.setInt(1, materialbatch.getMaterialID());
			ps.setDouble(2, materialbatch.getQuantity());
			ps.setInt(3, materialbatch.getMbID());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
