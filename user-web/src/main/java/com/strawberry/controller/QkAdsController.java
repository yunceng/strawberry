package com.strawberry.controller;

import com.strawberry.bean.QKAdsBean;
import com.strawberry.service.QKAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 功能：提供广告的访问接口
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
@RequestMapping("ads")
@Controller
public class QkAdsController {

    @Autowired
    private QKAdsService qkAdsService;

    /**
     * 提供查询广告的访问接口逻辑
     *
     * @return
     */
    @RequestMapping("findBy")
    @ResponseBody
    public List<QKAdsBean> findBy(){
       return qkAdsService.findBy();
    }
}
