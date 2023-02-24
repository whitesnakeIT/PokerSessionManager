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
import pl.coderslab.pokersessionmanager.enums.TournamentType;

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
//@Table(name = TournamentLocal.TABLE_NAME)
@DiscriminatorValue(value = TournamentLocal.TOURNAMENT_GENUS)
public class TournamentLocal extends AbstractTournament {

//    public static final String TABLE_NAME = "tournaments_local";

    public static final String TOURNAMENT_GENUS = "local";
    @Transient
    private final TournamentGenus tournamentGenus = TournamentGenus.LOCAL;

    @ManyToOne
    @ToString.Exclude
    private Player player;


}