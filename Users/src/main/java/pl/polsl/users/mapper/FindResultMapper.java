package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.users.api.model.AdminFindResultModelApi;
import pl.polsl.users.api.model.CustomerFindResultModelApi;
import pl.polsl.users.api.model.WorkerFindResultModelApi;
import pl.polsl.users.dto.FindResultDto;
import pl.polsl.users.dto.UserDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AdminMapper.class, WorkerMapper.class})
public interface FindResultMapper {

    AdminFindResultModelApi mapAdminDtoToModelApi(FindResultDto<UserDto> findResultDto);
    CustomerFindResultModelApi mapCustomerDtoToModelApi(FindResultDto<UserDto> findResultDto);
    WorkerFindResultModelApi mapWorkerDtoToModelApi(FindResultDto<UserDto> findResultDto);
}
