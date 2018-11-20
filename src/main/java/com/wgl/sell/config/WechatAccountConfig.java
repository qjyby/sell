package com.wgl.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众号id
     */
    private String mpAppId;
    /**
     * 公众号秘钥
     */
    private String mpAppSecret;


    /**
     * 开放平台id
     */
    private String opendAppId;
    /**
     * 开放平台秘钥
     */
    private String openAppSecret;

    /**
     * 商户号
     */

    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 异步通知
     */
    private String notifyUrl;

    /**
     微信推送模板
     */
    private Map<String,String>templateId;
}
