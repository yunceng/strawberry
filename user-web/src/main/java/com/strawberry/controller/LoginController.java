package com.strawberry.controller;

import com.strawberry.conf.JWTAudienceConfig;
import com.strawberry.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private JWTAudienceConfig jwtAudienceConfig;

    @RequestMapping("login.html")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        //拼装accessToken
        String accessToken = JwtHelper.createJWT("JWT","username", jwtAudienceConfig);
        return "index";
    }

}
