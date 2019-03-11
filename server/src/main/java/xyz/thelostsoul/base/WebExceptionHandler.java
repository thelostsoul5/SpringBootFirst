package xyz.thelostsoul.base;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<Object> unknownAccount(UnknownAccountException e) {
        String error = "用户名/密码错误";
        LOG.error(error, e);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> unknownAccount(IncorrectCredentialsException e) {
        String error = "用户名/密码错误";
        LOG.error(error, e);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> excessiveAttempts(ExcessiveAttemptsException e) {
        String error = "登录失败多次，账户锁定10分钟";
        LOG.error(error, e);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<Object> authentication(AuthenticationException e) {
        String error = "其他认证错误：" + e.getMessage();
        LOG.error(error, e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> unknownException(Exception e) {
        String error = "发生了未知异常：" + e.getMessage();
        LOG.error(error, e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
