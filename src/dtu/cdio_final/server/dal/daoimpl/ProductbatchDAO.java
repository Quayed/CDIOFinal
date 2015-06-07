package dtu.cdio_final.server.dal.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IProductbatchDAO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class ProductbatchDAO implements IProductbatchDAO {

	
	@Override
	public ProductbatchDTO getProductbatch(int pbId) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("SELECT formula_id, status FROM productbatch WHERE pb_id = ?");
			ps.setInt(1, pbId);
			ResultSet rs = ps.executeQuery();
			if (!rs.first()) {
				return null;
			} else {
				return new ProductbatchDTO(pbId, rs.getInt("formula_id"), rs.getInt("status"));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ProductbatchDTO> getProductbatchList() throws DALException {
		List<ProductbatchDTO> list = new ArrayList<ProductbatchDTO>();
		try {
			ResultSet rs = Connector.doQuery("SELECT pb_id, formula_id, status FROM productbatch");
			while (rs.next()) {
				list.add(new ProductbatchDTO(rs.getInt("pb_id"), rs.getInt("formula_id"), rs.getInt("status")));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createProductbatch(ProductbatchDTO produktbatch) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("INSERT INTO productbatch (pb_id, formula_id, status) VALUES (?,?,?)");
			ps.setInt(1, produktbatch.getPbID());
			ps.setInt(2, produktbatch.getFormulaID());
			ps.setInt(3, produktbatch.getStatus());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public void updateProductbatch(ProductbatchDTO produktbatch) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("UPDATE productbatch SET formula_id = ?, status = ? WHERE pb_id = ?");
			ps.setInt(1, produktbatch.getFormulaID());
			ps.setInt(2, produktbatch.getStatus());
			ps.setInt(3, produktbatch.getPbID());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
