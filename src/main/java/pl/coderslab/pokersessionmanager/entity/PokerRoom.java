package pl.coderslab.pokersessionmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = PokerRoom.TABLE_NAME)
public class PokerRoom {
    public static final String TABLE_NAME = "poker_rooms";

//    @Enumerated(EnumType.STRING)
    private PokerRoomScope pokerRoomScope;
    //    private String scope;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @URL
    @NotEmpty
    private String url;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokerRoom")
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AbstractTournament> tournamentList = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PokerRoom pokerRoom = (PokerRoom) o;
        return id != null && Objects.equals(id, pokerRoom.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
