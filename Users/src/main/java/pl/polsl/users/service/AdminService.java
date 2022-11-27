package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.SearchDto;
import pl.polsl.users.dto.UserDto;

import pl.polsl.users.entity.Admin;
import pl.polsl.users.entity.User;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.AdminMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.repository.AdminRepository;

import java.util.stream.Collectors;

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
        userDto.setId(admin.getId());

        return userDto;
    }

    public UserDto getSingleAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(admin.getUserId());

        UserDto userDto = adminMapper.mapEntityToDto(admin);

        return userMapper.mapModelApiToDto(user, userDto);
    }

    public FindResultDto<UserDto> findAdmins(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Admin> page = adminRepository.findAll(pageRequest);

        return FindResultDto.<UserDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(adminMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public UserDto updateAdmin(UserDto userDto, User user, boolean isSelfUpdate) {
        Admin mappedAdmin = adminMapper.mapDtoToEntity(userDto);
        mappedAdmin.setId(user.getId());
        mappedAdmin.setUserId(user.getUserId());

        adminRepository.save(mappedAdmin);

        userDto.setRole("admin");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(mappedAdmin.getId());

        return userDto;
    }

    public UserDto mapAdmin(User user) {
        Admin admin =  (Admin) user;

        return adminMapper.mapEntityToDto(admin);
    }
}
