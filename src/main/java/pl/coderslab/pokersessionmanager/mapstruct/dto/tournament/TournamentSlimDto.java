package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

@Data
public class TournamentSlimDto {

    private Long Id;

    private String name;

    private String type;

    private String speed;

    private double buyIn;

}
