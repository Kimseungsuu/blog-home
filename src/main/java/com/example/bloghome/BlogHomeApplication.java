package com.example.bloghome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogHomeApplication.class, args);
    }
}
