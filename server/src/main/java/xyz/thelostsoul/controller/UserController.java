package xyz.thelostsoul.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.service.inter.IUserService;

import java.util.List;

/**
 * Created by jamie on 17-2-13.
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/user/{id}", method= RequestMethod.GET)
    public ResponseEntity<Object> queryUserMessage(@PathVariable int id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Object> allUsers(@RequestParam(name="pageNum") int pageNum, @RequestParam(name="pageSize") int pageSize){
        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<User> userList = userService.allUsers();
        return new ResponseEntity<>(new PageInfo<>(userList), HttpStatus.OK);
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public ResponseEntity<Object> addUser(@RequestParam(name="name") String name, @RequestParam(name="password") String password){
        Integer count = userService.addUser(new User(name, password));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
