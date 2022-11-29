package pl.coderslab.pokersessionmanager.entity;

import org.hibernate.validator.constraints.URL;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = PokerRoom.TABLE_NAME)
public class PokerRoom {
    public static final String TABLE_NAME = "poker_rooms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @URL
    @NotEmpty
    private String url;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "poker_room_id")
    private List<TournamentGlobal> tournamentGlobalList;

}
