package pl.coderslab.pokersessionmanager.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.URL;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = PokerRoom.TABLE_NAME)
public class PokerRoom {
    public static final String TABLE_NAME = "poker_rooms";

    private String scope;
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
    private List<AbstractTournament> tournamentList = new java.util.ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private User user;

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
