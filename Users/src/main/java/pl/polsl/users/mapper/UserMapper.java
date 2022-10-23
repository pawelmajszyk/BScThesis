package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.users.api.model.UserRequestModelApi;
import pl.polsl.users.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AddressMapper.class)
public interface UserMapper {

    UserDto mapModelApiToDto(UserRequestModelApi userRequestModelApi);
}
