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
import pl.polsl.users.entity.Manager;
import pl.polsl.users.entity.User;
import pl.polsl.users.entity.Worker;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.repository.ManagerRepository;
import pl.polsl.users.repository.WorkerRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final KeycloakService keycloakService;
    private final ManagerRepository managerRepository;
    private final WorkerMapper workerMapper;
    private final WorkerRepository workerRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createManager(UserDto userDto) {
        Manager manager = managerRepository.save(workerMapper.mapDtoToManagerEntity(userDto));

        userDto.setRole("manager");

        String userId = keycloakService.addUser(userDto);

        manager.setUserId(userId);
        userDto.setId(userId);

        return userDto;
    }

    @Transactional
    public UserDto createWorker(UserDto userDto) {

        Worker save = workerRepository.save(workerMapper.mapDtoToWorkerEntity(userDto));

        userDto.setRole("worker");

        String userId = keycloakService.addUser(userDto);

        userDto.setId(userId);
        save.setUserId(userId);

        return userDto;
    }

    public UserDto getSingleWorker(Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(worker.getUserId());

        UserDto userDto = workerMapper.mapEntityToDto(worker);

        return userMapper.mapModelApiToDto(user, userDto);
    }

    public UserDto getSingleManager(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(manager.getUserId());

        UserDto userDto = workerMapper.mapEntityToDto(manager);

        return userMapper.mapModelApiToDto(user, userDto);
    }

    public FindResultDto<UserDto> findWorkers(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Worker> page = workerRepository.findAll(pageRequest);

        return FindResultDto.<UserDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(workerMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public FindResultDto<UserDto> findManagers(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Manager> page = managerRepository.findAll(pageRequest);

        return FindResultDto.<UserDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(workerMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public UserDto updateManager(UserDto userDto, User user, boolean isSelfUpdate) {
        Long addressId = managerRepository.findById(user.getId()).get().getAddress().getId();
        Manager mappedManager = workerMapper.mapDtoToManagerEntity(userDto);

        mappedManager.setId(user.getId());
        mappedManager.setUserId(user.getUserId());
        mappedManager.getAddress().setId(addressId);

        managerRepository.save(mappedManager);

        userDto.setRole("manager");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(user.getUserId());

        return userDto;
    }

    public UserDto updateWorker(UserDto userDto, User user, boolean isSelfUpdate) {
        Long addressId = workerRepository.findById(user.getId()).get().getAddress().getId();
        Worker mappedWorker = workerMapper.mapDtoToWorkerEntity(userDto);

        mappedWorker.setId(user.getId());
        mappedWorker.setUserId(user.getUserId());
        mappedWorker.getAddress().setId(addressId);

        workerRepository.save(mappedWorker);

        userDto.setRole("worker");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(user.getUserId());

        return userDto;
    }
}
