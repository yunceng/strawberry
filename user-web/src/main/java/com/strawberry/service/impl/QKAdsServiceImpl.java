package com.strawberry.service.impl;

import com.strawberry.bean.QKAdsBean;
import com.strawberry.dao.QKAdsDao;
import com.strawberry.service.QKAdsService;
import com.strawberry.util.bean.PageViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：对QKAdsService接口进行实现
 *
 * @author alan
 * @version 1.0.0
 * @since 2018.09.27
 */
@Service("qkAdsService")
public class QKAdsServiceImpl implements QKAdsService {

    @Autowired
    private QKAdsDao qkAdsDao;

    /**
     * 实现广告的查询封装
     *
     * @return
     */
    @Override
    public List<QKAdsBean> findBy() {

        PageViewBean pageViewBean = new PageViewBean();
        pageViewBean.setStartRow(10);
        pageViewBean.setPageSize(100);
        return qkAdsDao.findByPage(pageViewBean);
    }
}
