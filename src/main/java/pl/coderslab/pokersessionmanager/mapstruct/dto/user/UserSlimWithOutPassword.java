package pl.coderslab.pokersessionmanager.mapstruct.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.coderslab.pokersessionmanager.entity.user.User} entity
 */
@Data
public class UserSlimWithOutPassword implements Serializable {
    private final Long id;
    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 15)
    private final String username;
}