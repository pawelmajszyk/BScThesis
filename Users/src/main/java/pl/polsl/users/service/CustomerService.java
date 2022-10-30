package pl.polsl.users.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Customer;
import pl.polsl.users.entity.Manager;
import pl.polsl.users.exceptions.UserNotFoundException;
import pl.polsl.users.mapper.CustomerMapper;
import pl.polsl.users.mapper.UserMapper;
import pl.polsl.users.repository.CustomerRepository;

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
        userDto.setId(userId);

        return userDto;
    }

    public UserDto getSingleCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserRepresentation user = keycloakService.getUser(customer.getUserId());

        UserDto userDto = customerMapper.mapEntityToDto(customer);

        return userMapper.mapModelApiToDto(user, userDto);
    }
}
