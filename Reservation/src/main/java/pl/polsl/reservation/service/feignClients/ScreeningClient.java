package pl.polsl.reservation.service.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.screening.api.model.ScreeningModelApi;

@FeignClient("Screening")
public interface ScreeningClient {

    @GetMapping("/screening/{id}")
    ScreeningModelApi getSingleScreening(@PathVariable Long id);
}
