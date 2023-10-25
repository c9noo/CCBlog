package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
public class CBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CBlogApplication.class,args);
        System.out.println("SpringBoot，启动!");
    }
}