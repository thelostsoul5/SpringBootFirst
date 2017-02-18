package xyz.thelostsoul.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.dao.UserMapper;
import xyz.thelostsoul.service.inter.IUserService;

import java.util.List;

/**
 * Created by jamie on 17-2-13.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userDao;

    @Override
    public User getUserById(int id){
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<User> allUsers(){
        return userDao.allUsers();
    }

    @Override
    public int addUser(User user){
        return userDao.insertUser(user);
    }
}
