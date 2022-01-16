package com.collegevol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE","OPTIONS" };
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
