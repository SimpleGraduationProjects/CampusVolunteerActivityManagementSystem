package com.collegevol;

import com.collegevol.vo.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication(scanBasePackages = "com.collegevol")
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.collegevol.dao")
//@ServletComponentScan(basePackages = "com.collegevol")
@EnableScheduling
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        new Test().test();
    }

}
