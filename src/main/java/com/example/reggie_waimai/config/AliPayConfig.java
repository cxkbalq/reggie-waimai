package com.example.reggie_waimai.config;

/**
 * 支付宝支付配置类
 */
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    @Value("${alipay.appId}")
    private String appId;
    @Value("${alipay.appPrivateKey}")
    private String appPrivateKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
}