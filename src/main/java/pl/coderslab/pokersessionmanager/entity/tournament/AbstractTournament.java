package pl.coderslab.pokersessionmanager.entity.tournament;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tournament_scope", discriminatorType = DiscriminatorType.STRING)
@Table(name = "tournaments")
public abstract class AbstractTournament {

//    public static final String TABLE_NAME = "tournaments";

//    public static final String TOURNAMENT_SCOPE_COLUMN = "tournament_scope";

    @Transient
    protected TournamentScope tournamentScope;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    @NotEmpty
    private String speed;
    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "10000.00")
    private double buyIn;
    @NotNull
    private boolean reBuy;
    @NotNull
    @Min(2)
    @Max(10)
    private int handed;
    @Transient
    private String concatFields;
    @ManyToOne
    @JoinColumn(name = "poker_room_id")
    private PokerRoom pokerRoom;

    public String getConcatFields() {
        return name + " " + type + " " + speed;


    }
    @ManyToOne
    @ToString.Exclude
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractTournament that = (AbstractTournament) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
