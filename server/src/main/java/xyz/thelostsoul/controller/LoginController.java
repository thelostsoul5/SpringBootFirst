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
            error = "����Ϊ��";
            response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getId()), user.getPassword());

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "�û���/�������";
            response = new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        } catch (ExcessiveAttemptsException e) {
            error = "��¼ʧ�ܶ�Σ��˻�����10����";
            response = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        } catch (AuthenticationException e) {
            // �������󣬱�������������뵥�������뵥��catch����
            error = "��������" + e.getMessage();
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
