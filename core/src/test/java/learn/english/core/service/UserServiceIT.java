package learn.english.core.service;

import learn.english.core.test.DataBaseService;
import learn.english.model.entity.User;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.io.IOException;

@RunWith(Arquillian.class)
public class UserServiceIT {
    @EJB
    UserService userService;
    @EJB
    DataBaseService dbService;

    @Before
    public void initDB() throws Exception{
        dbService.insert();
    }

    @After
    public void dropDB()throws Exception{
        dbService.clear();
    }

    @Test
    public void mockTest() throws IOException {
        System.out.println(userService.find(1L));
        User user = new User();
        user.setName("Antuan");
        user.setPassword("1234");
        user.setEmail("qwe@gmail.com");
        userService.addToDataBase(user);
        System.out.println(userService.find(2L));
    }

    @Test
    public void mockTest2() throws Exception {
        System.out.println("Inside UserServiceIT test22");
        System.out.println(userService.find(1L));
        System.out.println(userService.findByNameAndPassword("Antuan", "1234"));
    }
}