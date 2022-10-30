package pl.polsl.users.service;

import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.polsl.users.entity.User;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation userRepresentation = keycloakService.deleteUser(user.getUserId());

        return userRepresentation.getUsername();
    }

    public String updatePassword(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return keycloakService.resetPassword(user.getUserId());
    }
}
