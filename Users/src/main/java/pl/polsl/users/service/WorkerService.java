package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Manager;
import pl.polsl.users.entity.Worker;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.repository.ManagerRepository;
import pl.polsl.users.repository.WorkerRepository;

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
}
