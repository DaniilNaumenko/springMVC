package by.naumenka.service.impl;

import by.naumenka.dao.UserDao;
import by.naumenka.exception.UserNotFoundException;
import by.naumenka.model.User;
import by.naumenka.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(long userId) {
        LOGGER.info("getUserById " + userId);
        return userDao.readUser(userId);
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        LOGGER.info("getUserByEmail " + email);
        return userDao.getAllUsers()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found by email " + email));
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        LOGGER.info("getUsersByName " + name);
        return userDao.getAllUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .sorted(Comparator.comparing(User::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) throws UserNotFoundException {
        LOGGER.info("createUser " + user);
        Long maxId = userDao.getAllUsers().stream()
                .max(Comparator.comparing(User::getId))
                .map(User::getId)
                .orElseThrow(() -> new UserNotFoundException("There are no users in storage"));

        user.setId(maxId + 1);
        userDao.createUser(user);
        return userDao.readUser(user.getId());
    }

    @Override
    public User updateUser(long id, User user) {
        LOGGER.info("updateUser " + user);
        userDao.updateUser(id, user);
        return userDao.readUser(user.getId());
    }

    @Override
    public boolean deleteUser(long userId) {
        LOGGER.info("deleteUser " + userId);
        User deletedUser = userDao.deleteUser(userId);
        return deletedUser != null;
    }
}