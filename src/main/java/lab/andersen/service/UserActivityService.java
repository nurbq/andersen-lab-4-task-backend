package lab.andersen.service;

import lab.andersen.exception.ServiceException;
import lab.andersen.model.UserActivity;
import lab.andersen.model.UserActivityShort;

import java.util.List;

public interface UserActivityService {

    List<UserActivity> findAllUsersActivities() throws ServiceException;

    List<UserActivityShort> findAllTodayActivities() throws ServiceException;

    UserActivity findById(int id) throws ServiceException;

    UserActivity create(UserActivity userActivity) throws ServiceException;

    UserActivity update(UserActivity userActivity) throws ServiceException;

    void delete(int id) throws ServiceException;
}
