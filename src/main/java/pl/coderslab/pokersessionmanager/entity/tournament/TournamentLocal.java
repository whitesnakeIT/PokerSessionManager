package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.entity.user.Player;

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

    @ManyToOne
    @ToString.Exclude
    private Player player;


}