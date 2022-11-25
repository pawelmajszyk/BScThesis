package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.cinema.api.model.CinemaModelApi;
import pl.polsl.users.api.controller.CustomerApi;
import pl.polsl.users.api.model.AdminFindResultModelApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerFindResultModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.SearchDto;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.CustomerMapper;
import pl.polsl.users.mapper.FindResultMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.service.CustomerService;
import pl.polsl.users.service.clients.CinemaClient;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@RestController
public class CustomerController implements CustomerApi {

    private final UserMapper userMapper;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final FindResultMapper findResultMapper;

    @Override
   // @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<WorkerResponseModelApi> createCustomer(UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        userDto = customerService.createCustomer(userDto);

        return new ResponseEntity<>(customerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
//    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<WorkerResponseModelApi> getSingleCustomer(Long id) {
        UserDto userDto = customerService.getSingleCustomer(id);

        return new ResponseEntity<>(customerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
//    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<WorkerFindResultModelApi> getCustomers(Long limit, Integer page) {
        SearchDto searchDto = SearchDto.builder()
                .limit(limit)
                .page(page)
                .build();

        FindResultDto<UserDto> findResultDto = customerService.findCustomers(searchDto);

        WorkerFindResultModelApi result = findResultMapper.mapWorkerDtoToModelApi(findResultDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
