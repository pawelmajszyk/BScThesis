package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.WorkerApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.service.WorkerService;


@RequiredArgsConstructor
@RestController
public class WorkerController implements WorkerApi {

    private final WorkerService workerService;
    private final UserMapper userMapper;
    private final WorkerMapper workerMapper;

    @Override
    public ResponseEntity<WorkerResponseModelApi> createWorker(UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        userDto = workerService.createWorker(userDto);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WorkerResponseModelApi> getSingleWorker(Long id) {
        UserDto userDto = workerService.getSingleWorker(id);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
