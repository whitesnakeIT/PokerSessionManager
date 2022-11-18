package pl.coderslab.pokersessionmanager.mapstruct.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Data
public class UserGetDto {

    private Long Id;

    private String firstName;


    private String lastName;


    private String fullName;

    @Email
    @NotNull
    private String email;

}
