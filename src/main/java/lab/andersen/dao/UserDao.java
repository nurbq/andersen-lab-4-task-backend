package lab.andersen.dao;

import lab.andersen.exception.DaoException;
import lab.andersen.model.DTO.UserDTO;
import lab.andersen.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<UserDTO> findAll() throws DaoException;
    Optional<UserDTO> findById(int id) throws DaoException;

    Optional<User> findByUsername(String username) throws DaoException;

    UserDTO create(User entity) throws DaoException;

    UserDTO update(UserDTO entity) throws DaoException;

    void delete(int id) throws DaoException;
}
