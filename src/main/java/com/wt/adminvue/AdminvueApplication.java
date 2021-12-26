package com.wt.adminvue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wt.adminvue.mapper")
@SpringBootApplication
public class AdminvueApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminvueApplication.class, args);
    }

}
