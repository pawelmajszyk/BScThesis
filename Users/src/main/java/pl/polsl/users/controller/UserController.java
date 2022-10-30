package pl.polsl.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.users.api.controller.UserApi;
import pl.polsl.users.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<String> deleteUSer(Long id) {
        String response = userService.deleteUser(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> resetPassword(Long id) {
        String response = userService.updatePassword(id);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
