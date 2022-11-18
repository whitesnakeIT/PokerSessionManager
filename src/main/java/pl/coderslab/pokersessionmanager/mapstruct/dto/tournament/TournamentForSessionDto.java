package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TournamentForSessionDto {

    private String name;

    private String type;

    private String speed;

    private double buyIn;

    private boolean reBuy;

    private int handed;

    LocalDateTime tournamentStartDateTime;
}
