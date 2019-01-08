package xyz.thelostsoul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(@RequestParam(name="id")String id,
                        @RequestParam(name="passwd")String passwd,
                        HttpSession session) throws Exception {
        session.setAttribute("SESSION_KEY", id);
        return "success";
    }
}
