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
        String error;
        ResponseEntity<String> response = new ResponseEntity<>("success", HttpStatus.OK);
        if (user.getPassword() == null) {
            error = "密码为空";
            response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getId()), user.getPassword());

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
            response = new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败多次，账户锁定10分钟";
            response = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
