package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.users.api.model.AdminResponseModelApi;
import pl.polsl.users.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {

    AdminResponseModelApi mapDtoToModelApi(UserDto userDto);
}
