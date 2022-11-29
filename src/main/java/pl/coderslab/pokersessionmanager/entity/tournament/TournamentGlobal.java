package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = TournamentGlobal.TABLE_NAME)
public class TournamentGlobal extends AbstractTournament {
    public static final String TABLE_NAME = "tournaments";



}
