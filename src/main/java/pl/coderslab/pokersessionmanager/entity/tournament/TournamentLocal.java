package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
//@Table(name = TournamentLocal.TABLE_NAME)
@DiscriminatorValue(TournamentLocal.TOURNAMENT_SCOPE)
public class TournamentLocal extends AbstractTournament {

//    public static final String TABLE_NAME = "tournaments_local";

    public static final String TOURNAMENT_SCOPE = "local";

    public TournamentLocal() {
        super();
        this.tournamentScope = TournamentScope.LOCAL;
    }

}