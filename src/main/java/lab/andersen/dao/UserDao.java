package lab.andersen.dao;

import lab.andersen.entity.User;
import lab.andersen.exception.DaoException;
import lab.andersen.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT id, first_name, second_name, age FROM users where id = ?";
    private static final String FIND_ALL = "SELECT id, first_name, second_name, age FROM users";
    private static final String SAVE_USER = "INSERT INTO users(first_name, second_name, age) VALUES(?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET first_name = ? ,second_name = ?, age = ? WHERE id = ?";


    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public void insert(String firstName, String secondName, Integer age) {
        try (
                Connection connection = ConnectionManager.open();
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)
        ) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = ConnectionManager.open();
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(FIND_ALL);

            while (rs.next()) {
                users.add(new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("second_name"),
                                rs.getInt("age")
                        )
                );
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return users;
    }

    public boolean updateById(Integer id, String firstName, String secondName, Integer age) {
        try (
                Connection connection = ConnectionManager.open();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean deleteById(Integer id) {
        try (
                Connection connection = ConnectionManager.open();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)
        ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
