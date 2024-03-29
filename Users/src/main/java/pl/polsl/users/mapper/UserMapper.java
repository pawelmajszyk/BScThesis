package pl.polsl.users.mapper;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AddressMapper.class)
public interface UserMapper {


    UserDto mapModelApiToDto(UserRequestModelApi userRequestModelApi);

    WorkerResponseModelApi mapDtoToModelApi(UserDto userDto);

    @Mapping(target = "username", source = "userRepresentation.username")
    @Mapping(target = "id", source = "userDto.id")
    @Mapping(target = "firstName", source = "userDto.firstName")
    @Mapping(target = "lastName", source = "userDto.lastName")
    @Mapping(target = "email", source = "userRepresentation.email")
    UserDto mapModelApiToDto(UserRepresentation userRepresentation, UserDto userDto);

    UserDto mapEntityToDto(User user);
}
