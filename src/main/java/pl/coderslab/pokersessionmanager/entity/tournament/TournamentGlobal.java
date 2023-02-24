package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;

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

    @Transient
    private final TournamentGenus tournamentGenus = TournamentGenus.GLOBAL;

//    @ManyToOne
//    @ToString.Exclude
//    private Player player;
}
