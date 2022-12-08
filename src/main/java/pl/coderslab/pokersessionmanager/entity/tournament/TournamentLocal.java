package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.*;
import pl.coderslab.pokersessionmanager.entity.user.User;

import javax.persistence.*;

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
    private User user;


}