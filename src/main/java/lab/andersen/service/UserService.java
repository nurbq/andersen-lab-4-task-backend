package lab.andersen.service;

import lab.andersen.exception.ServiceException;
import lab.andersen.model.DTO.UserDTO;
import lab.andersen.model.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers() throws ServiceException;

    UserDTO findById(int id) throws ServiceException;

    User findByUsername(String username) throws ServiceException;

    UserDTO create(User user) throws ServiceException;

    UserDTO update(UserDTO user) throws ServiceException;

    void delete(int id) throws ServiceException;
}
