package com.download.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                //允许跨域的路径
        registry.addMapping("/**")
                //允许跨域的域名
                .allowedOriginPatterns("*")
                //是否允许存储cookie
                .allowCredentials(true)
                //允许请求的方式
                .allowedMethods("GET", "POST", "DELETE","PUT")
                //允许请求携带的请求头
                .allowedHeaders("*")
                //允许跨域的最大时间
                .maxAge(3600);
    }
}
