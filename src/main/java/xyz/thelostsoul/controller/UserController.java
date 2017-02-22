package xyz.thelostsoul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.service.inter.IUserService;

import java.util.List;

/**
 * Created by jamie on 17-2-13.
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/user/{id}", method= RequestMethod.GET)
    public User queryUserMessage(@PathVariable int id){
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> allUsers(){
        return userService.allUsers();
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public int addUser(@RequestParam(name="name") String name, @RequestParam(name="password") String password){
        return userService.addUser(new User(name, password));
    }



}
