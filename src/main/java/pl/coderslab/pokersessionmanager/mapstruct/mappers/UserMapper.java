package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithOutPassword;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithPassword;

@Mapper(componentModel = "spring")

public interface UserMapper {


    User userToUserSlimWithPasswordToUser(UserSlimWithPassword userSlimWithPassword);

    UserSlimWithPassword userToUserToUserSlimWithPassword(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserToUserSlimWithPassword(UserSlimWithPassword userSlimWithPassword, @MappingTarget User user);

    User userToUserSlimWithOutPasswordToUser(UserSlimWithOutPassword userSlimWithOutPassword);

    UserSlimWithOutPassword userToUserToUserSlimWithOutPassword(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserToUserSlimWithOutPassword(UserSlimWithOutPassword userSlimWithOutPassword, @MappingTarget User user);
}
