package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class TournamentLocalDto {
    private final Long Id;
    @NotNull
    @NotEmpty
    private final String name;
    @NotNull
    @NotEmpty
    private final String type;
    @NotNull
    @NotEmpty
    private final String speed;
    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "5000.00")
    private final double buyIn;
    @NotNull
    private final boolean reBuy;
    @NotNull
    @Min(2)
    @Max(10)
    private final int handed;
    private final LocalDateTime tournamentStartDateTime;
}
