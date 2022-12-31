package pl.polsl.cinemahall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class CinemaHallApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaHallApplication.class, args);
    }

}
