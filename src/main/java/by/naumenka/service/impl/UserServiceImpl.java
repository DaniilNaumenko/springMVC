package by.naumenka.service.impl;

import by.naumenka.dao.UserDao;
import by.naumenka.exception.GlobalApplicationException;
import by.naumenka.exception.UserNotFoundException;
import by.naumenka.model.User;
import by.naumenka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(long userId) {
        log.info("getUserById " + userId);
        return userDao.getAllUsers()
                .stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found by id " + userId));
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("getUserByEmail " + email);
        return userDao.getAllUsers()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found by email " + email));
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("getUsersByName " + name);
        return userDao.getAllUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .sorted(Comparator.comparing(User::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        if (isEmailNotUnique(user.getEmail())) {
            log.error("Email is not unique");
            throw new GlobalApplicationException("User with such email is already present");
        }

        Optional<Long> maxId = userDao.getAllUsers().stream()
                .max(Comparator.comparing(User::getId))
                .map(User::getId);

        if (maxId.isPresent()) {
            user.setId(maxId.get() + 1L);
        } else {
            user.setId(1L);
        }
        log.info("created user:  " + user);
        return userDao.createUser(user);
    }

    private boolean isEmailNotUnique(String email) {
        Optional<User> foundEmail = userDao.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
        return foundEmail.isPresent();
    }

    @Override
    public User updateUser(long id, User user) {
        log.info("updateUser " + user);
        if (isEmailNotUnique(user.getEmail())) {
            log.error("Email is not unique");
            throw new GlobalApplicationException("User with such email is already present");
        }
        userDao.getAllUsers().stream()
                .max(Comparator.comparing(User::getId))
                .map(User::getId)
                .ifPresent(user::setId);

        return userDao.updateUser(id, user);
    }

    @Override
    public boolean deleteUser(long userId) {
        log.info("deleteUser " + userId);
        User deletedUser = userDao.deleteUser(userId);
        return deletedUser != null;
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
}