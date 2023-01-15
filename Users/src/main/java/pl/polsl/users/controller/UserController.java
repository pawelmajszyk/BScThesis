package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.UserApi;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.service.UserService;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;
    private final UserMapper userMapper;
    private final WorkerMapper workerMapper;

    @Override
    @RolesAllowed({"admin", "manager", "worker", "customer"})
    public ResponseEntity<WorkerResponseModelApi> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserDto userDto = userService.getSelfInfo(currentPrincipalName);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin", "manager", "worker", "customer"})
    public ResponseEntity<WorkerResponseModelApi> deleteUSer(Long id) {
        String response = userService.deleteUser(id);

        return new ResponseEntity<>(new WorkerResponseModelApi().id(id), HttpStatus.ACCEPTED);
    }

    @Override
    @RolesAllowed({"admin", "manager", "worker", "customer"})
    public ResponseEntity<WorkerResponseModelApi> resetPassword(Long id) {
        UserDto userDto = userService.updatePassword(id, null);

        return new ResponseEntity<>(userMapper.mapDtoToModelApi(userDto), HttpStatus.ACCEPTED);
    }

    @Override
    @RolesAllowed({"admin", "manager", "worker", "customer"})
    public ResponseEntity<WorkerResponseModelApi> selfUpdateUser(UserRequestModelApi userRequestModelApi) {
        UserDto userDto = userMapper.mapModelApiToDto(userRequestModelApi);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        userDto = userService.updateUser(userDto, currentPrincipalName, null, true);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin", "manager", "worker", "customer"})
    public ResponseEntity<WorkerResponseModelApi> selfResetPassword() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserDto userDto = userService.updatePassword(null, currentPrincipalName);

        return new ResponseEntity<>(workerMapper.mapDtoToModelApi(userDto), HttpStatus.OK);
    }
}
