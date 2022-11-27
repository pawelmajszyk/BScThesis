package pl.polsl.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.users.api.model.WorkerResponseModelApi;

@FeignClient("Users")
public interface UsersClient {

    @GetMapping("/manager/{id}")
    WorkerResponseModelApi getSingleManager(@PathVariable Long id);

    @GetMapping("/user")
    WorkerResponseModelApi getSelfInfo();

    @GetMapping("/worker/{id}")
    WorkerResponseModelApi getSingleWorker(@PathVariable Long id);
}

