package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
@Table(name = TournamentLocal.TABLE_NAME)
public class TournamentLocal extends AbstractTournament {

    public static final String TABLE_NAME = "user_local_tournaments";


}