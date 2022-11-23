package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.Mapper;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithPasswordDto;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserBasicInfoWithPasswordDto userToUserBasicInfoWithPasswordDto(User user);
    UserBasicInfoWithOutPasswordDto userToUserBasicInfoWithOutPasswordDto(User user);

}
