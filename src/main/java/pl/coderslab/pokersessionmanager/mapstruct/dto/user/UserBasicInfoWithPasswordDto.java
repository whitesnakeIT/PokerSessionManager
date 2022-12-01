package pl.coderslab.pokersessionmanager.mapstruct.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserBasicInfoWithPasswordDto {

    private Long id;
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;


    private String fullName;

    @Email
    @NotNull
    private String email;
//@Password do zrobienia validator czy haslo takie samo
    @NotNull
    private String password;


}
