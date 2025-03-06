package com.mycompany.teamproject9;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.mycompany.teamproject9.repository")  // 🔹 Mapper 패키지 스캔

public class  TeamProject9Application {

    public static void main(String[] args) {
        SpringApplication.run(TeamProject9Application.class, args);
    }

}
