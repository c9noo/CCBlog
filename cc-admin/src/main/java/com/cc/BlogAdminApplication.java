package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: CCBlog
 * @ClassName BlogAdminApplication
 * @author: c9noo
 * @create: 2023-10-21 11:32
 * @Version 1.0
 **/
@SpringBootApplication
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
        System.out.println("SpringBoot启动!");
    }
}
