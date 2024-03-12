package lab.andersen.dao;

import lab.andersen.exception.DaoException;
import lab.andersen.exception.UserNotFoundException;
import lab.andersen.model.User;
import lab.andersen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserUserDaoImpl implements UserDao {

    private static final String FIND_ALL_USERS = "SELECT id, age, surname FROM users";
    private static final String FIND_USER_BY_ID = "SELECT id, age, surname FROM users WHERE id = ?";
    private static final String CREATE_USER = "INSERT INTO users(age, surname) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET age = ?, surname = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("age"), resultSet.getString("surname"));
                user.setId(resultSet.getInt("id"));
                allUsers.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return allUsers;
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("age"), resultSet.getString("surname"));
                user.setId(resultSet.getInt("id"));
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void create(User entity) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setInt(1, entity.getAge());
            statement.setString(2, entity.getSurname());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User entity) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            if (findById(entity.getId()).isPresent()) {
                statement.setInt(1, entity.getAge());
                statement.setString(2, entity.getSurname());
                statement.setInt(3, entity.getId());
                statement.executeUpdate();
                connection.commit();
            } else {
                throw new UserNotFoundException(String.format("User with id=%d doesn't exist", entity.getId()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
