package com.example.cinema;

import com.example.cinema.entity.Cinema;
import com.example.cinema.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class CinemaApplication implements CommandLineRunner {

    private final CinemaRepository cinemaRepository;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(cinemaRepository.findAll().isEmpty()) {
            cinemaRepository.save(Cinema.builder().city("Katowce").isEnabled(true).name("Katowice").street("Bytomska")
                    .zipCode("41-253").build());
        }
    }
}
