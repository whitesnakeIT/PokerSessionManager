package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Data;
import pl.coderslab.pokersessionmanager.entity.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Data
@Table(name = TournamentSuggestion.TABLE_NAME)
public class TournamentSuggestion extends AbstractTournament{
    public static final String TABLE_NAME = "user_suggestions_tournaments";
//    @ManyToOne
//    private User user;
}
