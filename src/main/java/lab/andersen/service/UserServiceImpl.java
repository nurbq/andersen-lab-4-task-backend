package lab.andersen.service;

import lab.andersen.dao.UserDao;
import lab.andersen.exception.DaoException;
import lab.andersen.exception.ServiceException;
import lab.andersen.exception.UserNotFoundException;
import lab.andersen.model.DTO.UserDTO;
import lab.andersen.model.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDTO> findAllUsers() throws ServiceException {
        List<UserDTO> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public UserDTO findById(int id) throws ServiceException {
        try {
            Optional<UserDTO> optionalUser = userDao.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new UserNotFoundException(String.format("User with id=%d doesn't exist", id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByUsername(String username) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findByUsername(username);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new UserNotFoundException(String.format("User with Username=%s doesn't exist", username));
        } catch (DaoException | UserNotFoundException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO create(User user) throws ServiceException {
        UserDTO createdUser = null;
        try {
            createdUser = userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return createdUser;
    }

    @Override
    public UserDTO update(UserDTO user) throws ServiceException {
        UserDTO updatedUser = null;
        try {
            updatedUser = userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return updatedUser;
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
