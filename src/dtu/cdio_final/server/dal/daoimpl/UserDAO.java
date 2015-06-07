package dtu.cdio_final.server.dal.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.server.dal.daointerfaces.IUserDAO;
import dtu.cdio_final.shared.dto.UserDTO;

public class UserDAO implements IUserDAO {
	
	
	public UserDTO getUser(int userID) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("SELECT user_name, ini, cpr, password FROM operator WHERE user_id = ?");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.first()) {
				return null;
			} else {
				return new UserDTO(userID, rs.getString("user_name"), rs.getString("ini"), rs.getString("cpr"),
					rs.getString("password"), 0, 0);
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	public List<UserDTO> getUserList() throws DALException {
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery("SELECT user_id, user_name, ini, cpr, password FROM user");
			while (rs.next()) {
				list.add(new UserDTO(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("ini"), rs.getString("cpr"),
						rs.getString("password"), 0, 0));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
	
	public void createUser(UserDTO user) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("INSERT INTO user(user_id, user_name, ini, cpr, password) VALUES " + "(?, ?, ?, ?, ?)");
			ps.setString(1, null);
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getIni());
			ps.setString(4, user.getCpr());
			ps.setString(5, user.getPassword());
			ps.execute();
			user.setUserID(Connector.getLastInsert(ps));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	public void updateUser(UserDTO user) throws DALException {
		try {
			PreparedStatement ps = Connector.prepare("UPDATE user SET user_name = ?, ini = ?, cpr = ?, password = ? WHERE user_id = ?");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getIni());
			ps.setString(3, user.getCpr());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getUserID());
			ps.execute();
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
