package by.naumenka.dao;

import by.naumenka.config.WebConfigurationTest;
import by.naumenka.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {

    ApplicationContext applicationContext;
    UserDao userDao;
    User user;

    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext(WebConfigurationTest.class);
        userDao = applicationContext.getBean(UserDao.class);

        user = Mockito.mock(User.class);
        userDao.createUser(user);
    }

    @Test
    public void createUserTest() {
        Assert.assertEquals(user, userDao.readUser(user.getId()));
    }

    @Test
    public void readUserTest() {
        Assert.assertEquals(user, userDao.readUser(user.getId()));
    }

    @Test
    public void updateUserTest() {
        Assert.assertEquals(user, userDao.updateUser(user.getId(), user));
    }

    @Test
    public void deleteUserTest() {
        Assert.assertEquals(user, userDao.deleteUser(user.getId()));
    }

    @Test
    public void getAllUsersTest() {
        Assert.assertNotNull(userDao.getAllUsers());
    }
}