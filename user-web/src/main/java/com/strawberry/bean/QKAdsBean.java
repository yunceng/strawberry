package com.strawberry.bean;

import java.io.Serializable;

/**
 * 数据库持久类
 *
 * 功能：广告
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
public class QKAdsBean implements Serializable {

    //唯一标识符
    private int id;
    //图片地址
    private String img;
    //图片备注
    private String alt;
    //国家
    private String country;
    //渠道
    private String channel;
    //类型：0为轮播图，1为展板图
    private int type;
    //状态：0为线上，1为下线
    private int status;
    //应用地址
    private String app_url;
    //应用名
    private String appName;
    //应用商店
    private String appStore;
    //应用描述
    private String description;
    //应用价格
    private double priceF;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppStore() {
        return appStore;
    }

    public void setAppStore(String appStore) {
        this.appStore = appStore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPriceF() {
        return priceF;
    }

    public void setPriceF(double priceF) {
        this.priceF = priceF;
    }
}
