package by.naumenka.dao;

import by.naumenka.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest {

    ApplicationContext applicationContext;
    UserDao userDao;
    User user;

    @Before
    public void setup(){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userDao = applicationContext.getBean("userDao", UserDao.class);

        user = Mockito.mock(User.class);
        userDao.createUser(user);
    }

    @Test
    public void createUserTest(){
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
