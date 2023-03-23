package pl.coderslab.pokersessionmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;

import java.util.Objects;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@ToString
@Table(name = Session.TABLE_NAME)
public class Session {

    public static final String TABLE_NAME = "sessions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotEmpty
    private String name;

    private double totalCost = 0;

    private Integer tournamentCount = 0;
    @NotEmpty
    @ManyToMany
    @JoinTable(
            name = "sessions_tournaments",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private TreeSet<AbstractTournament> sessionTournaments = new TreeSet<>();
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;


    public double getTotalCost() {

        double sum = sessionTournaments.stream().mapToDouble(AbstractTournament::getBuyIn
        ).sum();
        return Math.round(sum * 100d) / 100d;
    }

    public Integer getTournamentCount() {
        return sessionTournaments.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Session session = (Session) o;
        return Id != null && Objects.equals(Id, session.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
