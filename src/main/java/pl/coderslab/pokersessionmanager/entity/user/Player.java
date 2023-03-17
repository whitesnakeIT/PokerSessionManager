package pl.coderslab.pokersessionmanager.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = Player.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@DiscriminatorValue(value = Player.USER_TYPE)
public class Player extends User {
    //    public static final String TABLE_NAME = "players";
    public static final String USER_TYPE = "player";
    @ManyToMany
    @JoinTable(
            name = "player_favourite_tournaments",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)

    @ToString.Exclude
    private List<AbstractTournament> favouriteTournaments = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<TournamentSuggestion> suggestedTournaments = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<TournamentLocal> localTournaments = new ArrayList<>();


    @OneToMany(mappedBy = "player")
    @ToString.Exclude
    private List<Session> sessions = new ArrayList<>();

    @OneToOne(mappedBy = "player")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlayerStats playerStats;

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<PokerRoom> pokerRoomsLocal = new ArrayList<>();
}
