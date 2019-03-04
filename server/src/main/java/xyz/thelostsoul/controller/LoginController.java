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
            error = "�û���/�������";
        } catch (ExcessiveAttemptsException e) {
            error = "��¼ʧ�ܶ�Σ��˻�����10����";
        } catch (AuthenticationException e) {
            // �������󣬱�������������뵥�������뵥��catch����
            error = "��������" + e.getMessage();
        }
        if (error != null) {
            // �����ˣ����ص�¼ҳ��
            return "failure:" + error;
        } else {// ��¼�ɹ�
            return "success";
        }
    }
}
