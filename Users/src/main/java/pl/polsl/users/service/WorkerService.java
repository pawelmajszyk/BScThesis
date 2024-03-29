package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.SearchDto;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Customer;
import pl.polsl.users.entity.Manager;
import pl.polsl.users.entity.User;
import pl.polsl.users.entity.Worker;
import pl.polsl.users.exceptions.CinemaNotFoundException;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.mapper.WorkerMapper;
import pl.polsl.users.repository.ManagerRepository;
import pl.polsl.users.repository.WorkerRepository;
import pl.polsl.users.service.clients.CinemaClient;

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
    private final CinemaClient cinemaClient;

    @Transactional
    public UserDto createManager(UserDto userDto, Long id) {
        try {
            cinemaClient.getSingleCinema(id);
        } catch (Exception e) {
            throw new CinemaNotFoundException();
        }

        Manager manager = managerRepository.save(workerMapper.mapDtoToManagerEntity(userDto, id));

        userDto.setRole("manager");

        String userId = keycloakService.addUser(userDto);

        userDto.setId(manager.getId());
        manager.setUserId(userId);

        return userDto;
    }

    @Transactional
    public UserDto createWorker(UserDto userDto, Long id) {
        try {
            cinemaClient.getSingleCinema(id);
        } catch (Exception e) {
            throw new CinemaNotFoundException();
        }

        Worker save = workerRepository.save(workerMapper.mapDtoToWorkerEntity(userDto, id));

        userDto.setRole("worker");

        String userId = keycloakService.addUser(userDto);

        userDto.setId(save.getId());
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

    public FindResultDto<UserDto> findWorkers(SearchDto searchDto, Long cinemaId) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Example<Worker> example = Example.of(Worker.builder().isEnabled(true).cinemaId(cinemaId).build());

        Page<Worker> page = workerRepository.findAll(example, pageRequest);

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
        Manager manager = managerRepository.findById(user.getId()).get();

        Manager mappedManager = workerMapper.mapDtoToManagerEntity(userDto, manager.getCinemaId());

        mappedManager.setId(user.getId());
        mappedManager.setUserId(user.getUserId());
        mappedManager.getAddress().setId(addressId);

        managerRepository.save(mappedManager);

        userDto.setRole("manager");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(mappedManager.getId());

        return userDto;
    }

    public UserDto updateWorker(UserDto userDto, User user, boolean isSelfUpdate) {
        Long addressId = workerRepository.findById(user.getId()).get().getAddress().getId();
        Worker worker = workerRepository.findById(user.getId()).get();
        Worker mappedWorker = workerMapper.mapDtoToWorkerEntity(userDto, worker.getCinemaId());

        mappedWorker.setId(user.getId());
        mappedWorker.setUserId(user.getUserId());
        mappedWorker.getAddress().setId(addressId);

        workerRepository.save(mappedWorker);

        userDto.setRole("worker");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(mappedWorker.getId());

        return userDto;
    }

    public UserDto mapWorker(User user) {
        Worker worker = (Worker) user;

        UserRepresentation keycloackUser = keycloakService.getUser(worker.getUserId());

        UserDto userDto = workerMapper.mapEntityToDto(worker);

        return userMapper.mapModelApiToDto(keycloackUser, userDto);
    }

    public UserDto mapManager(User user) {
        Manager manager = (Manager) user;

        UserRepresentation keycloackUser = keycloakService.getUser(manager.getUserId());

        UserDto userDto = workerMapper.mapEntityToDto(manager);

        return userMapper.mapModelApiToDto(keycloackUser, userDto);
    }

    public FindResultDto<UserDto> findWorkersForCinema(SearchDto searchDto, String currentPrincipalName) {
        Manager manager = managerRepository.findByUserId(currentPrincipalName);

        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Example<Worker> example = Example.of(Worker.builder().isEnabled(true).cinemaId(manager.getCinemaId()).build());

        Page<Worker> page = workerRepository.findAll(example, pageRequest);

        return FindResultDto.<UserDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(workerMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
