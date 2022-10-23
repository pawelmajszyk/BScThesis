package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.users.dto.UserDto;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final KeycloakService keycloakService;

    public UserDto createAdmin(UserDto userDto) {
        userDto.setRole("ADMIN");

        String userId = keycloakService.addUser(userDto);

        userDto.setId(userId);

        return userDto;
    }
}
