package xyz.thelostsoul.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.dao.UserMapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by jamie on 17-2-26.
 */
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserById() throws Exception {
        User user = new User(1,"j","z");
        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        User u = userService.getUserById(1);
        assertEquals(user,u);
    }

    @Test
    public void allUsers() throws Exception {

    }

    @Test
    public void addUser() throws Exception {

    }

}