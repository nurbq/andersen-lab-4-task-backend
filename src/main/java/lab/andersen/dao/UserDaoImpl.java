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

public class UserDaoImpl implements UserDao {

    private static final String FIND_ALL_USERS = "SELECT id, age, surname, name FROM users order by id;";
    private static final String FIND_USER_BY_ID = "SELECT id, age, surname, name FROM users WHERE id = ?";
    private static final String CREATE_USER = "INSERT INTO users(age, surname, name) VALUES (?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET age = ?, surname = ?, name = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";


    @Override
    public List<User> findAll() throws DaoException {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getInt("age"),
                        resultSet.getString("surname"),
                        resultSet.getString("name")
                );
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
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getInt("age"),
                        resultSet.getString("surname"),
                        resultSet.getString("name")
                );
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User entity) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getAge());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getName());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt("id"));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return entity;
    }

    @Override
    public User update(User entity) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            if (findById(entity.getId()).isPresent()) {
                statement.setInt(1, entity.getAge());
                statement.setString(2, entity.getSurname());
                statement.setString(3, entity.getName());
                statement.setInt(4, entity.getId());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setName(generatedKeys.getString("name"));
                        entity.setSurname(generatedKeys.getString("surname"));
                        entity.setAge(generatedKeys.getInt("age"));
                    }
                    else {
                        throw new SQLException("Updating user failed.");
                    }
                }

                connection.commit();
            } else {
                throw new UserNotFoundException(String.format("User with id=%d doesn't exist", entity.getId()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return entity;
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
