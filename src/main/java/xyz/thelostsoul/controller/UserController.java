package xyz.thelostsoul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.service.impl.UserServiceImpl;

import java.util.List;

/**
 * Created by jamie on 17-2-13.
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value="/user/{id}", method= RequestMethod.GET)
    public User queryUserMessage(@PathVariable int id){
        return userServiceImpl.getUserById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> allUsers(){
        return userServiceImpl.allUsers();
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public int addUser(@RequestParam(name="name") String name, @RequestParam(name="password") String password){
        return userServiceImpl.addUser(new User(name, password));
    }



}
