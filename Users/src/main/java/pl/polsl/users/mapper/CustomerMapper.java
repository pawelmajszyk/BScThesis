package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Customer;
import pl.polsl.users.entity.Manager;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AddressMapper.class)
public interface CustomerMapper {

    WorkerResponseModelApi mapDtoToModelApi(UserDto userDto);

    @Mapping(target = "userId", source = "id")
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "isEnabled", defaultValue = "true")
    Customer mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(Customer customer);
}
