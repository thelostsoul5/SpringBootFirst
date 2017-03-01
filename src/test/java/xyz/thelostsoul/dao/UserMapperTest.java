package xyz.thelostsoul.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.thelostsoul.bean.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jamie on 17-2-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper dao;

    @Test
    public void selectByPrimaryKey() throws Exception {
        User user = dao.selectByPrimaryKey(1);
        assertNotNull(user);
    }

    @Test
    public void allUsers() throws Exception {

    }

    @Test
    public void insertUser() throws Exception {

    }

    @Test
    public void selectByIds() throws Exception {
        List<Integer> ids = new ArrayList<Integer>(){{
            add(1);
            add(2);
        }};
        List<User> users = dao.selectByIds(ids);
        assertNotNull(users);
    }

}