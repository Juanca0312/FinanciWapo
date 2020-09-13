package com.econowapo.financiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinanciappApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanciappApplication.class, args);
    }

}
