package com.collegevol.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 文件相关的配置
 */
@Configuration
public class ImgFileConfig extends WebMvcConfigurerAdapter {

    @Value("${image.dir}")
    private String IMAGE_DIR;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(IMAGE_DIR);
        super.addResourceHandlers(registry);
    }
}
