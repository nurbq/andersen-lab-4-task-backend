package lab.andersen.dao;

import lab.andersen.exception.DaoException;
import lab.andersen.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll() throws DaoException;
    Optional<User> findById(int id) throws DaoException;

    User create(User entity) throws DaoException;

    User update(User entity) throws DaoException;

    void delete(int id) throws DaoException;
}
