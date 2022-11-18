package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalTime;

@Data
public class TournamentDtoList {

    private Long Id;

    private String name;

    private String type;

    private String speed;

    private double buyIn;

    private boolean reBuy;

    private int handed;

    LocalTime startingTime;

}
