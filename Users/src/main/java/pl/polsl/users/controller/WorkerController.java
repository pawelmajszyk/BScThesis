package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.WorkerApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerFindResultModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.SearchDto;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.FindResultMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.service.WorkerService;

import javax.annotation.security.RolesAllowed;


@RequiredArgsConstructor
@RestController
public class WorkerController implements WorkerApi {

    private final WorkerService workerService;
    private final UserMapper userMapper;
    private final WorkerMapper workerMapper;
    private final FindResultMapper findResultMapper;

    @Override
    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<WorkerResponseModelApi> createWorker(Long id, UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        userDto = workerService.createWorker(userDto, id);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }


    @Override
    @RolesAllowed({"admin", "manager"})
    @CrossOrigin
    public ResponseEntity<WorkerResponseModelApi> getSingleWorker(Long id) {
        UserDto userDto = workerService.getSingleWorker(id);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<WorkerFindResultModelApi> getWorkers(Long limit, Integer page) {
        SearchDto searchDto = SearchDto.builder()
                .limit(limit)
                .page(page)
                .build();

        FindResultDto<UserDto> findResultDto = workerService.findWorkers(searchDto);

        WorkerFindResultModelApi result = findResultMapper.mapWorkerDtoToModelApi(findResultDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
