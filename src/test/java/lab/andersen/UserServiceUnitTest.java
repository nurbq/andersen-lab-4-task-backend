package lab.andersen;


import lab.andersen.dao.UserDao;
import lab.andersen.exception.DaoException;
import lab.andersen.exception.ServiceException;
import lab.andersen.model.User;
import lab.andersen.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void findUserById() throws DaoException, ServiceException {
        User expectedUser = new User(1, 15, "Bide", "Joe");

        when(userDao.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findById(expectedUser.getId());

        verify(userDao, times(1)).findById(expectedUser.getId());

        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
    }

}
