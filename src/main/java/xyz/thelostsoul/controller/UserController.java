package xyz.thelostsoul.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<User> allUsers(@RequestParam(name="pageNum") int pageNum, @RequestParam(name="pageSize") int pageSize){
        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<User> userList = userService.allUsers();
        return new PageInfo<>(userList);
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public int addUser(@RequestParam(name="name") String name, @RequestParam(name="password") String password){
        return userService.addUser(new User(name, password));
    }



}
