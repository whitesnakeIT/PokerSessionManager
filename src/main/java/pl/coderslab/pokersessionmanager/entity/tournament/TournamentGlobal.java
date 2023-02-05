package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
//@Table(name = TournamentGlobal.TABLE_NAME)
@DiscriminatorValue(value = TournamentGlobal.TOURNAMENT_GENUS)
public class TournamentGlobal extends AbstractTournament {
    //    public static final String TABLE_NAME = "tournaments_global";
    public static final String TOURNAMENT_GENUS = "global";


}
