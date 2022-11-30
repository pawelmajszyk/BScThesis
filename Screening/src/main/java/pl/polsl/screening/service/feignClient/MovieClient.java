package pl.polsl.screening.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.movie.api.model.MovieModelApi;

@FeignClient("Movie")
public interface MovieClient {

    @GetMapping("/movie/{id}")
    MovieModelApi getSingleMovie(@PathVariable Long id);
}
