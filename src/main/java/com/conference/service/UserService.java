package com.conference.service;


import com.conference.Component;
import com.conference.dao.UserDao;
import com.conference.data.entity.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public User getById(Integer id) throws ServiceException {
        return Optional.ofNullable(userDao.findById(id)).orElseThrow(() -> new ServiceException("Could not find user with id " + id));
    }

    public Optional<User> findMatchingCredentials(String email, String password) {
        return userDao.findByEmail(email).map(user -> password.equalsIgnoreCase(user.getPassword()) ? user : null);
    }

    public void delete(Integer id) {
        userDao.removeById(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}