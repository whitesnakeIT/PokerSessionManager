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
//@Table(name = TournamentSuggestion.TABLE_NAME)
@DiscriminatorValue(TournamentSuggestion.TOURNAMENT_SCOPE)
public class TournamentSuggestion extends AbstractTournament {
    //    public static final String TABLE_NAME = "tournament_suggestions";
    public static final String TOURNAMENT_SCOPE = "suggestion";

    public TournamentSuggestion() {
        super();
        this.tournamentScope = TournamentScope.SUGGESTION;
    }

}


