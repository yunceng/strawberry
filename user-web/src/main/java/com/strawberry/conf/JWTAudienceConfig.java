package com.strawberry.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 功能：根据jwt.properties文件生成JWT的配置类
 *
 * @author alan.wnag
 * @version 1.0.0
 * @since 2018.09.30
 */

@Component
@ConfigurationProperties(prefix = "audience")
@PropertySource("classpath:jwt.properties")
public class JWTAudienceConfig {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getBase64Secret() {
        return base64Secret;
    }
    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getExpiresSecond() {
        return expiresSecond;
    }
    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
