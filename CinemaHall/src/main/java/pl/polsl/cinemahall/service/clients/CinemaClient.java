package pl.polsl.cinemahall.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.cinema.api.model.CinemaModelApi;

@FeignClient("Cinema")
public interface CinemaClient {

    @GetMapping("/cinema/{id}")
    CinemaModelApi getSingleCinema(@PathVariable Long id);
}
