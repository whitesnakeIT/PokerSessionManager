package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
//@Table(name = TournamentSuggestion.TABLE_NAME)
@DiscriminatorValue(value = TournamentSuggestion.TOURNAMENT_GENUS)
public class TournamentSuggestion extends AbstractTournament {
    public static final String TABLE_NAME = "tournaments_suggestion";
    public static final String TOURNAMENT_GENUS = "suggestion";

    @ManyToOne
    private User user;
}


