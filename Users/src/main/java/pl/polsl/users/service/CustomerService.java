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
import pl.polsl.users.entity.Customer;
import pl.polsl.users.entity.User;
import pl.polsl.users.exceptions.CinemaNotFoundException;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.CustomerMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.repository.CustomerRepository;
import pl.polsl.users.repository.UserRepository;
import pl.polsl.users.service.clients.CinemaClient;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KeycloakService keycloakService;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createCustomer(UserDto userDto) {
        Customer customer = customerRepository.save(customerMapper.mapDtoToEntity(userDto));

        userDto.setRole("customer");

        String userId = keycloakService.addUser(userDto);

        customer.setUserId(userId);
        userDto.setId(customer.getId());

        return userDto;
    }

    public UserDto getSingleCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(customer.getUserId());

        UserDto userDto = customerMapper.mapEntityToDto(customer);

        return userMapper.mapModelApiToDto(user, userDto);
    }

    public FindResultDto<UserDto> findCustomers(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Customer> page = customerRepository.findAllByIsEnabledTrue(pageRequest);

        return FindResultDto.<UserDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(customerMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public UserDto updateCustomer(UserDto userDto, User user, boolean isSelfUpdate) {
        Customer mappedCustomer = customerMapper.mapDtoToEntity(userDto);
        mappedCustomer.setId(user.getId());
        mappedCustomer.setUserId(user.getUserId());

        customerRepository.save(mappedCustomer);

        userDto.setRole("customer");

        keycloakService.updateUser(userDto, user.getUserId(), isSelfUpdate);

        userDto.setId(mappedCustomer.getId());

        return userDto;
    }

    public UserDto mapCustomer(User user) {
        Customer customer = (Customer) user;

        UserRepresentation keycloackUser = keycloakService.getUser(customer.getUserId());

        UserDto userDto = customerMapper.mapEntityToDto(customer);

        return userMapper.mapModelApiToDto(keycloackUser, userDto);
    }
}
