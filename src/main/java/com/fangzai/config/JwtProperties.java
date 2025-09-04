package com.fangzai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    
    /**
     * JWT秘钥
     */
    private String secret;
    
    /**
     * JWT过期时间(毫秒)
     */
    private Long expiration;
    
    /**
     * JWT请求头
     */
    private String header;
    
    /**
     * JWT前缀
     */
    private String prefix;
    
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public Long getExpiration() {
        return expiration;
    }
    
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
    
    public String getHeader() {
        return header;
    }
    
    public void setHeader(String header) {
        this.header = header;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}