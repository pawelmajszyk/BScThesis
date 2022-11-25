package pl.polsl.users.service;

import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Admin;
import pl.polsl.users.entity.Manager;
import pl.polsl.users.entity.User;
import pl.polsl.users.entity.Worker;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final AdminService adminService;
    private final WorkerService workerService;
    private final CustomerService customerService;
    private final UserMapper userMapper;

    @Transactional
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setIsEnabled(false);

        UserRepresentation userRepresentation = keycloakService.deleteUser(user.getUserId());

        return userRepresentation.getUsername();
    }

    @Transactional
    public UserDto updatePassword(Long id, String currentPrincipalName) {
        User user = null;

        if(id == null) {
            user = userRepository.findByUserId(currentPrincipalName).orElseThrow(() -> new UserNotFoundException(id));
        } else {
            user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        }

        keycloakService.resetPassword(user.getUserId());

        return userMapper.mapEntityToDto(user);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto, String currentPrincipalName, Long id, boolean isSelfUpdate) {
        User user = null;
        UserDto dto = null;

        if(currentPrincipalName == null) {
            user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(currentPrincipalName));
        } else {
            user = userRepository.findByUserId(currentPrincipalName).orElseThrow(() -> new UserNotFoundException(currentPrincipalName));
        }

        if(user instanceof Admin) {
            dto = adminService.updateAdmin(userDto, user, isSelfUpdate);
        } else if(user instanceof Manager) {
            dto = workerService.updateManager(userDto, user, isSelfUpdate);
        } else if(user instanceof Worker) {
            dto = workerService.updateWorker(userDto, user, isSelfUpdate);
        } else {
            dto = customerService.updateCustomer(userDto, user, isSelfUpdate);
        }

        dto.setAddress(userDto.getAddress());
        return dto;
    }

    public UserDto getSelfInfo(String currentPrincipalName) {
        UserDto dto = null;

        User user = userRepository.findByUserId(currentPrincipalName).orElseThrow(() -> new UserNotFoundException(currentPrincipalName));

        if(user instanceof Admin) {
            dto = adminService.mapAdmin(user);
        } else if(user instanceof Manager) {
            dto = workerService.mapManager(user);
        } else if(user instanceof Worker) {
            dto = workerService.mapWorker(user);
        } else {
            dto = customerService.mapCustomer(user);
        }

        return dto;
    }
}
