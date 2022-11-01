package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.users.api.model.AdminResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Admin;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {

    AdminResponseModelApi mapDtoToModelApi(UserDto userDto);

    @Mapping(target = "userId", source = "id")
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "isEnabled", defaultValue = "true")
    Admin mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(Admin admin);
}
