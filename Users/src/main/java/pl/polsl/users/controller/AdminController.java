package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.AdminApi;
import pl.polsl.users.api.model.AdminFindResultModelApi;
import pl.polsl.users.api.model.AdminResponseModelApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.SearchDto;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.AdminMapper;
import pl.polsl.users.mapper.FindResultMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.service.AdminService;
import pl.polsl.users.service.UserService;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {

    private final UserMapper userMapper;
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final UserService userService;
    private final FindResultMapper findResultMapper;
    private final WorkerMapper workerMapper;

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<AdminResponseModelApi> createAdmin(UserRequestModelApi adminModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(adminModelApi);

        userDto = adminService.createAdmin(userDto);

        return new ResponseEntity<>(adminMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<AdminResponseModelApi> getSingleAdmin(Long id) {
        UserDto userDto = adminService.getSingleAdmin(id);

        return new ResponseEntity<>(adminMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<AdminFindResultModelApi> getAdmins(Long limit, Integer page) {
        SearchDto searchDto = SearchDto.builder()
                .limit(limit)
                .page(page)
                .build();

        FindResultDto<UserDto> findResultDto = adminService.findAdmins(searchDto);

        AdminFindResultModelApi result = findResultMapper.mapAdminDtoToModelApi(findResultDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin", "manager", "worker"})
    public ResponseEntity<WorkerResponseModelApi> updateUser(Long id, UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        userDto = userService.updateUser(userDto, null, id, true);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
