package lab.andersen;

import lab.andersen.dao.UserActivityDao;
import lab.andersen.exception.DaoException;
import lab.andersen.exception.ServiceException;
import lab.andersen.model.UserActivity;
import lab.andersen.service.UserActivityServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserActivityUnitTest {

    @Mock
    private UserActivityDao dao;

    @InjectMocks
    private UserActivityServiceImpl service;

    public UserActivityUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllUsersActivities() throws Exception {
        List<UserActivity> expectedActivities = new ArrayList<>();
        expectedActivities.add(new UserActivity(1, 1, "test1", Timestamp.valueOf("2024-01-01 09:01:15")));
        expectedActivities.add(new UserActivity(2, 1, "test2", Timestamp.valueOf("2024-01-01 09:01:15")));
        when(dao.findAll()).thenReturn(expectedActivities);
        List<UserActivity> actualActivities = service.findAllUsersActivities();
        verify(dao, times(1)).findAll();
        assertEquals(expectedActivities.size(), actualActivities.size());
    }

    @Test
    public void findAllUsersActivitiesThrowsException() throws Exception {
        when(dao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> service.findAllUsersActivities());
        verify(dao, times(1)).findAll();
    }

    @Test
    public void findAllTodayActivities() throws Exception {
        Date date = new Date();
        List<UserActivity> expectedActivities = new ArrayList<>();
        expectedActivities.add(new UserActivity(1, 1, "test1", new Timestamp(date.getTime())));
        expectedActivities.add(new UserActivity(2, 1, "test2", new Timestamp(date.getTime())));
        when(dao.findAll()).thenReturn(expectedActivities);
        List<UserActivity> actualActivities = service.findAllUsersActivities();
        verify(dao, times(1)).findAll();
        assertEquals(expectedActivities.size(), actualActivities.size());
    }

    @Test
    public void findAllTodayActivitiesThrowsException() throws Exception {
        when(dao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> service.findAllUsersActivities());
        verify(dao, times(1)).findAll();
    }

    @Test
    public void updateUserActivity() throws Exception {
        UserActivity expectedUserActivity = new UserActivity(1, 1, "test1", Timestamp.valueOf("2024-01-01 09:01:15"));
        when(dao.update(expectedUserActivity)).thenReturn(expectedUserActivity);
        UserActivity actualUserActivity = service.update(expectedUserActivity);
        assertEquals(actualUserActivity, expectedUserActivity);
        verify(dao, times(1)).update(expectedUserActivity);
    }

    @Test
    public void updateUserActivityThrowsException() throws Exception {
        UserActivity activity = new UserActivity();
        doThrow(DaoException.class).when(dao).update(activity);
        assertThrows(ServiceException.class, () -> service.update(activity));
        verify(dao, times(1)).update(activity);
    }

    @Test
    public void createActivities() throws Exception {
        UserActivity expectedActivity = new UserActivity();
        when(dao.create(expectedActivity)).thenReturn(expectedActivity);
        UserActivity actualActivity = service.create(expectedActivity);
        assertEquals(expectedActivity, actualActivity);
        verify(dao, times(1)).create(expectedActivity);
    }

    @Test
    public void createActivitiesThrowsException() throws Exception {
        UserActivity activity = new UserActivity();
        doThrow(DaoException.class).when(dao).create(any(UserActivity.class));
        assertThrows(ServiceException.class, () -> service.create(activity));
        verify(dao, times(1)).create(activity);
    }

    @Test
    public void deleteActivity() throws Exception {
        int id = 1;
        doNothing().when(dao).delete(id);
        service.delete(id);
        verify(dao, times(1)).delete(id);
    }

    @Test
    public void deleteActivityThrowsException() throws Exception {
        int id = 1;
        doThrow(DaoException.class).when(dao).delete(anyInt());
        assertThrows(ServiceException.class, () -> service.delete(id));
        verify(dao, times(1)).delete(id);
    }
}
