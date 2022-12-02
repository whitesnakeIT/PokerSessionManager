package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
//@Table(name = TournamentGlobal.TABLE_NAME)
@DiscriminatorValue(value = TournamentGlobal.TOURNAMENT_GENUS)
public class TournamentGlobal extends AbstractTournament {
    public static final String TABLE_NAME = "tournaments_global";
    public static final String TOURNAMENT_GENUS = "global";


}
