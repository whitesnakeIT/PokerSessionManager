package pl.coderslab.pokersessionmanager.mapstruct.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Data
public class UserPostDto {

    private Long Id;

    private String firstName;


    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    private  boolean superAdmin;
}
