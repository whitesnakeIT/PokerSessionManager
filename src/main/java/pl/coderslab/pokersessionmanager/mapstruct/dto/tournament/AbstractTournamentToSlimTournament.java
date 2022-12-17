package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament} entity
 */
@Data
public class AbstractTournamentToSlimTournament implements Serializable {
    private final Long id;
    @NotNull
    @NotEmpty
    private final String name;
    @NotNull
    @NotEmpty
    private final String speed;
    @NotNull
    private final boolean reBuy;
}