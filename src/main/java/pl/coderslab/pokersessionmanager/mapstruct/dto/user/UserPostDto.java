package pl.coderslab.pokersessionmanager.mapstruct.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserPostDto {

    private Long Id;
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;


    private String fullName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;


}
