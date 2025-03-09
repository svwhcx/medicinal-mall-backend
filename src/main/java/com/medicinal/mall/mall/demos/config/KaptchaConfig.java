package com.medicinal.mall.mall.demos.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @description 图片验证码的一些配置
 * @Author cxk
 * @Date 2025/3/8 10:20
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 验证码文本字符颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 验证码图片宽度
        properties.setProperty("kaptcha.image.width", "110");
        // 验证码图片高度
        properties.setProperty("kaptcha.image.height", "40");
        // 验证码文本字符大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码存储在session中的key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码文本字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 验证码文本字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
