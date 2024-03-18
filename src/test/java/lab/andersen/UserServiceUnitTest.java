package lab.andersen;


import lab.andersen.dao.UserDao;
import lab.andersen.exception.DaoException;
import lab.andersen.exception.ServiceException;
import lab.andersen.exception.UserNotFoundException;
import lab.andersen.model.User;
import lab.andersen.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {


    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllUsers_returnsUsers() throws DaoException, ServiceException {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, 20, "TestName1", "testname2"));
        expectedUsers.add(new User(2, 21, "TestName2", "testname3"));

        when(userDao.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers();

        verify(userDao, times(1)).findAll();

        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    public void findUserById_returnsUser() throws DaoException, ServiceException {
        User expectedUser = new User(1, 15, "Biden", "Joe");

        when(userDao.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findById(expectedUser.getId());

        verify(userDao, times(1)).findById(expectedUser.getId());

        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    public void findUserById_throwsException() throws DaoException {
        when(userDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findById(anyInt())
        );
    }

    @Test
    public void createUser() throws DaoException, ServiceException {
        User user = new User();
        doNothing().when(userDao).create(user);

        userService.create(user);

        verify(userDao, times(1)).create(user);
    }

    @Test
    public void createUser_DaoException() throws DaoException {
        User user = new User();
        doThrow(DaoException.class).when(userDao).create(user);

        assertThrows(ServiceException.class, () -> userService.create(user));
    }
}
