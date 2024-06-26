package lab.andersen.dao;

import lab.andersen.exception.DaoException;
import lab.andersen.model.UserActivity;
import lab.andersen.model.UserActivityShort;

import java.util.List;
import java.util.Optional;

public interface UserActivityDao {

    List<UserActivity> findAll() throws DaoException;

    List<UserActivityShort> findAllToday() throws DaoException;

    Optional<UserActivity> findById(int id) throws DaoException;

    UserActivity create(UserActivity entity) throws DaoException;

    UserActivity update(UserActivity entity) throws DaoException;

    void delete(int id) throws DaoException;
    List<UserActivity> findAllAddUsername() throws DaoException;
}