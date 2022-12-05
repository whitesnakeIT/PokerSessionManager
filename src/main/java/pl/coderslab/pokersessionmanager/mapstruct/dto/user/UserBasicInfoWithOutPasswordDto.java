package pl.coderslab.pokersessionmanager.mapstruct.dto.user;

import lombok.Data;
import pl.coderslab.pokersessionmanager.entity.user.UserStats;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserBasicInfoWithOutPasswordDto {
    private Long id;
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;


    private String fullName;

    @Email
    @NotNull
    private String email;

    private UserStats userStats;

}
