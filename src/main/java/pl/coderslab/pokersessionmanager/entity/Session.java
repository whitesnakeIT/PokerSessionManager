package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = Session.TABLE_NAME)
public class Session {

    public static final String TABLE_NAME = "sessions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotEmpty
    private String name;

    private double totalCost = 0;

    private Integer tournamentCount;
    @NotEmpty
    @ManyToMany
    @JoinTable(
            name = "sessions_tournaments",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id")}
    )
    private List<AbstractTournament> sessionTournaments;
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Session{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", sessionTournaments=" + sessionTournaments +
                '}';
    }

    public double getTotalCost() {

        double sum = sessionTournaments.stream().mapToDouble(AbstractTournament::getBuyIn
        ).sum();
        return Math.round(sum * 100d) / 100d;
    }

    public Integer getTournamentCount() {
        return sessionTournaments.size();
    }


}
