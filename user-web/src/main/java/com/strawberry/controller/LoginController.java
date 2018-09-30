package com.strawberry.controller;

import com.strawberry.util.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping("login.html")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.getSubmitToken(request, response);
        return "index";
    }

}
