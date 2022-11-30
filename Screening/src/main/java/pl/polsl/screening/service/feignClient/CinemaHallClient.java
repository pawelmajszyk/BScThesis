package pl.polsl.screening.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;


@FeignClient("CinemaHall")
public interface CinemaHallClient {

    @GetMapping("/{id}")
    CinemaHallModelApi getSingleCinemaHall(@PathVariable Long id);
}
