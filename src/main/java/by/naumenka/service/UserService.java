package by.naumenka.service;

import by.naumenka.exception.UserNotFoundException;
import by.naumenka.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long userId);

    User getUserByEmail(String email) throws UserNotFoundException;

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(long id, User user);

    boolean deleteUser(long userId);

    List<User> getAllUsers();
}