package com.strawberry.service;

import com.strawberry.bean.QKAdsBean;

import java.util.List;

/**
 * 业务层接口
 *
 * 功能：提供访问广告业务的接口
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
public interface QKAdsService {

    /**
     * 功能：查询广告
     *
     * @return
     */
    public List<QKAdsBean> findBy();

}
