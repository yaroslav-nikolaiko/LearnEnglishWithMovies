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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UserServiceIT {
    @EJB
    UserService userService;
    @EJB
    DataBaseService dbSesrvice;

    @Before
    public void initDB() throws Exception{
        System.out.println("Inside UserServiceIT");String query =
                new String(Files.readAllBytes(Paths.get("src/test/resources/insert.sql")),"UTF-8");
        System.out.println(query);
        dbSesrvice.executeNativeQuery(query);
    }

    @After
    public void dropDB()throws Exception{
/*        List<String> list = dbSesrvice.executeNativeQuery2("SHOW TABLES");
        dbSesrvice.executeNativeQuery("SET REFERENTIAL_INTEGRITY FALSE");
        for (Object o : list) {
            dbSesrvice.executeNativeQuery("TRUNCATE TABLE TEST"+o);
        }*/
    }

    @Test
    public void mockTest() throws IOException {


        System.out.println(userService.find(1L));
   /*     User user = new User();
        user.setName("Antuan");
        user.setPassword("1234");
        user.setEmail("qwe@gmail.com");
        userService.addToDataBase(user);*/
    }

/*    @Test
    public void mockTest2() throws Exception {
        System.out.println("Inside UserServiceIT test22");
        System.out.println(userService.findByNameAndPassword("Antuan", "1234"));
    }*/
}