package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.ManagerApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.service.WorkerService;

@RequiredArgsConstructor
@RestController
public class ManagerController implements ManagerApi {

    private final WorkerService workerService;
    private final UserMapper userMapper;
    private final WorkerMapper workerMapper;

    @Override
    public ResponseEntity<WorkerResponseModelApi> createManager(UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        System.out.println("XD");

        userDto = workerService.createManager(userDto);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WorkerResponseModelApi> getSingleManager(Long id) {
        UserDto userDto = workerService.getSingleManager(id);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
