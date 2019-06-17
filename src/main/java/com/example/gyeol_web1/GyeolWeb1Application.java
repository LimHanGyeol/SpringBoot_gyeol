package com.example.gyeol_web1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GyeolWeb1Application {

    public static void main(String[] args) {
        SpringApplication.run(GyeolWeb1Application.class, args);
    }

}
