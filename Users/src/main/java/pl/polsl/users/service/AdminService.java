package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.UserDto;

import pl.polsl.users.entity.Admin;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.AdminMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final KeycloakService keycloakService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createAdmin(UserDto userDto) {
        Admin admin = adminRepository.save(adminMapper.mapDtoToEntity(userDto));

        userDto.setRole("admin");

        String userId = keycloakService.addUser(userDto);

        admin.setUserId(userId);
        userDto.setId(userId);

        return userDto;
    }

    public UserDto getSingleAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(admin.getUserId());

        UserDto userDto = adminMapper.mapEntityToDto(admin);
        UserDto userDto1 = userMapper.mapModelApiToDto(user, userDto);

        return userDto1;
    }
}
