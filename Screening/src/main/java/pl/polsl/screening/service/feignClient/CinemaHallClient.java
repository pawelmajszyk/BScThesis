package pl.polsl.screening.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;

import java.util.List;


@FeignClient("CinemaHall")
public interface CinemaHallClient {

    @GetMapping("/details/{id}")
    CinemaHallModelApi getSingleCinemaHall(@PathVariable Long id);

    @GetMapping("/{id}")
    List<CinemaHallModelApi> getCinemaHallsForCinema(@PathVariable Long id);
}
