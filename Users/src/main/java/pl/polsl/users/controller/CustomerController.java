package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.CustomerApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.CustomerMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.service.CustomerService;

@RequiredArgsConstructor
@RestController
public class CustomerController implements CustomerApi {

    private final UserMapper userMapper;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Override
    public ResponseEntity<WorkerResponseModelApi> createCustomer(UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        System.out.println("XD");

        userDto = customerService.createCustomer(userDto);

        return new ResponseEntity<>(customerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WorkerResponseModelApi> getSingleCustomer(Long id) {
        UserDto userDto = customerService.getSingleCustomer(id);

        return new ResponseEntity<>(customerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
