package com.keb.club_pila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClubPilaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubPilaApplication.class, args);
    }

}
