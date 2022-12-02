package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.*;
import pl.coderslab.pokersessionmanager.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
//@Table(name = TournamentLocal.TABLE_NAME)
@DiscriminatorValue(value = TournamentLocal.TOURNAMENT_GENUS)
public class TournamentLocal extends AbstractTournament {

    public static final String TABLE_NAME = "tournaments_local";

    public static final String TOURNAMENT_GENUS = "local";

    @ManyToOne
    private User user;


}