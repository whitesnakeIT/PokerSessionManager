package pl.coderslab.pokersessionmanager.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;

//import javax.persistence.*;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "player_favourite_tournaments",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id", referencedColumnName = "id")}
    )
    @ToString.Exclude
    private List<AbstractTournament> favouriteTournaments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<AbstractTournament> suggestedTournaments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<AbstractTournament> localTournaments = new ArrayList<>();


    @OneToMany(mappedBy = "player")
    @ToString.Exclude
    private List<Session> sessions;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private PlayerStats playerStats;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<PokerRoom> pokerRoomsLocal;

}
