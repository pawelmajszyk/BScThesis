package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.AdminApi;
import pl.polsl.users.api.model.AdminResponseModelApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.AdminMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.service.AdminService;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {

    private final UserMapper userMapper;
    private final AdminService adminService;
    private final AdminMapper adminMapper;

    @Override
    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<AdminResponseModelApi> createAdmin(UserRequestModelApi adminModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(adminModelApi);

        userDto = adminService.createAdmin(userDto);

        return new ResponseEntity<>(adminMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    @CrossOrigin
    public ResponseEntity<AdminResponseModelApi> getSingleAdmin(Long id) {
        UserDto userDto = adminService.getSingleAdmin(id);

        return new ResponseEntity<>(adminMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
