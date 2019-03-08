package xyz.thelostsoul.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thelostsoul.bean.User;

@RestController
@RequestMapping(value="/api")
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(User user) throws Exception {
        Subject subject = SecurityUtils.getSubject();

        ResponseEntity<String> response = new ResponseEntity<>("success", HttpStatus.OK);
        if (user.getId() == null || user.getPassword() == null) {
            response = new ResponseEntity<>("«Î«Û≤ªπÊ∑∂", HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getId()), user.getPassword());
        subject.login(token);

        return response;
    }
}
