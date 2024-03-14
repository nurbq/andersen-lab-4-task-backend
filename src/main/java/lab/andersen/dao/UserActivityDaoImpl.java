package lab.andersen.dao;

import lab.andersen.exception.DaoException;
import lab.andersen.exception.UserActivityNotFoundException;
import lab.andersen.model.UserActivity;
import lab.andersen.model.UserActivityShort;
import lab.andersen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserActivityDaoImpl implements UserActivityDao {

    private static final String FIND_ALL = "SELECT id, user_id, description, date_time FROM users_activities;";
    private static final String FIND_BY_ID = "SELECT id, user_id, description, date_time FROM z WHERE id = ?";
    private static final String CREATE_USER_ACTIVITY = "INSERT INTO users_activities(user_id, description) VALUES (?, ?)";
    private static final String UPDATE_USER_ACTIVITY = "UPDATE users_activities SET user_id = ?, description = ? WHERE id = ?";
    private static final String DELETE_USER_ACTIVITY = "DELETE FROM users_activities WHERE id = ?";
    private static final String FIND_ALL_TODAY_ACTIVITIES = "SELECT u.name, ua.description, ua.date_time " +
                                                            "FROM users_activities ua " +
                                                            "         left join users u on ua.user_id = u.id " +
                                                            "WHERE DATE(date_time) = CURRENT_DATE";

    @Override
    public List<UserActivity> findAll() throws DaoException {
        List<UserActivity> activities = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserActivity userActivity = userActivityBuilder(resultSet);
                activities.add(userActivity);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return activities;
    }

    @Override
    public List<UserActivityShort> findAllToday() throws DaoException {
        List<UserActivityShort> activities = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_TODAY_ACTIVITIES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserActivityShort userActivity = new UserActivityShort(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("date_time").toLocalDateTime()
                );
                activities.add(userActivity);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return activities;
    }

    @Override
    public Optional<UserActivity> findById(int id) throws DaoException {
        UserActivity userActivity = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userActivity = userActivityBuilder(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(userActivity);
    }

    @Override
    public void create(UserActivity entity) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_ACTIVITY)) {
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getDescription());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UserActivity entity) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ACTIVITY)) {
            if (findById(entity.getId()).isPresent()) {
                statement.setInt(1, entity.getUserId());
                statement.setString(2, entity.getDescription());
                statement.setInt(3, entity.getId());
                statement.executeUpdate();
                connection.commit();
            } else {
                throw new UserActivityNotFoundException("user activity with id=%d doesn't exist".formatted(entity.getId()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_ACTIVITY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserActivity userActivityBuilder(ResultSet resultSet) throws SQLException {
        return new UserActivity(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("description"),
                resultSet.getTimestamp("date_time").toLocalDateTime()
        );
    }

}
