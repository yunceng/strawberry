package com.strawberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 应用的首页
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 */

@Controller
public class IndexController {

    /**
     * 首页访问
     *
     * @return
     */
    @RequestMapping({"/index", ""})
    public String index() {
        return "index";
    }

}
