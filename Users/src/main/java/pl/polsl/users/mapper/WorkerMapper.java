package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.users.api.model.WorkerResponseModelApi;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Admin;
import pl.polsl.users.entity.Manager;
import pl.polsl.users.entity.Worker;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = AddressMapper.class)
public interface WorkerMapper {

    WorkerResponseModelApi mapDtoToModelApi(UserDto userDto);

    @Mapping(target = "userId", source = "userDto.id")
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "isEnabled", defaultValue = "true")
    @Mapping(target = "cinemaId", source = "cinemaId")
    Manager mapDtoToManagerEntity(UserDto userDto, Long cinemaId);

    @Mapping(target = "userId", source = "userDto.id")
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "isEnabled", defaultValue = "true")
    @Mapping(target = "cinemaId", source = "cinemaId")
    Worker mapDtoToWorkerEntity(UserDto userDto, Long cinemaId);

    UserDto mapEntityToDto(Worker worker);

    UserDto mapEntityToDto(Manager manager);
}
