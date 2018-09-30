package com.strawberry.util;

import com.strawberry.conf.JWTAudienceConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 功能：构造jwt及解析jwt的帮助类
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.30
 */
public class JwtHelper {

    public static final String JWT_HEADER_PARAM_TYPE_KEY = "typ";

    public static final String JWT_CLAIM_KEY = "userid";

    /**
     * 解析token值
     *
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成token值
     * @param userId
     * @param jwtAudienceConfig
     * @return
     */
    public static String createJWT(String jwtHeaderParamType, String userId, JWTAudienceConfig jwtAudienceConfig) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtAudienceConfig.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam(JWT_HEADER_PARAM_TYPE_KEY, jwtHeaderParamType)
                .claim(JWT_CLAIM_KEY, userId)
                .setIssuer(jwtAudienceConfig.getName())
                .setAudience(jwtAudienceConfig.getClientId())
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (jwtAudienceConfig.getExpiresSecond() >= 0) {
            long expMillis = nowMillis + jwtAudienceConfig.getExpiresSecond() * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }
}
