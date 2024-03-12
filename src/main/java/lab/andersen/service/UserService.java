package lab.andersen.service;

import lab.andersen.dao.UserDao;
import lab.andersen.entity.User;

import java.util.List;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<User> getAll() {
        return UserDao.getInstance().getAll();
    }

    public void insert(String firstName, String secondName, Integer age) {
        UserDao.getInstance().insert(firstName, secondName, age);
    }

    public boolean update(Integer id, String firstName, String secondName, Integer age) {
        return UserDao.getInstance().updateById(id, firstName, secondName, age);
    }

    public boolean deleteById(Integer id) {
        return UserDao.getInstance().deleteById(id);
    }
}
