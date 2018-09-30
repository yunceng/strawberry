package com.strawberry.dao;

import com.strawberry.bean.QKAdsBean;
import com.strawberry.util.bean.PageViewBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库访问接口
 * 功能：广告操作
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
@Repository
public interface QKAdsDao {

    /**
     * 方法名要和映射文件qkAdsMapper.xml中的id名字相同
     *
     * @return
     */
    public List<QKAdsBean> findByPage(PageViewBean pageViewBean);

}
