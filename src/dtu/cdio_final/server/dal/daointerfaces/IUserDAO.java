package dtu.cdio_final.server.dal.daointerfaces;

import java.util.List;

import dtu.cdio_final.shared.dto.UserDTO;

public interface IUserDAO {
	UserDTO getUser(int userId) throws DALException;

	List<UserDTO> getUserList() throws DALException;

	void createUser(UserDTO user) throws DALException;

	void updateUser(UserDTO user) throws DALException;
}
