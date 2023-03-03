package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
//@Table(name = TournamentGlobal.TABLE_NAME)
@DiscriminatorValue(TournamentGlobal.TOURNAMENT_SCOPE)
public class TournamentGlobal extends AbstractTournament {
//        public static final String TABLE_NAME = "tournament_globals";
    public static final String TOURNAMENT_SCOPE = "global";


public TournamentGlobal() {
    super();
    this.tournamentScope = TournamentScope.GLOBAL;
}


//    @ManyToOne
//    @ToString.Exclude
//    private Player player;
}
