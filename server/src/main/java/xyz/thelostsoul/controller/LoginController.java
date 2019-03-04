package xyz.thelostsoul.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import xyz.thelostsoul.bean.User;

@RestController
@RequestMapping(value="/api")
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(User user) throws Exception {
//        session.setAttribute("SESSION_KEY", id);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getId()), user.getPassword());
        String error = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败多次，账户锁定10分钟";
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }
        if (error != null) {
            // 出错了，返回登录页面
            return "failure:" + error;
        } else {// 登录成功
            return "success";
        }
    }
}
