package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.*;
import org.hibernate.Hibernate;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

//@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
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
    @DecimalMax(value = "5000.00")
    private double buyIn;
    @NotNull
    private boolean reBuy;
    @NotNull
    @Min(2)
    @Max(10)
    private int handed;
    //    @NotNull  for creating we dont't need starting date
    private LocalDateTime tournamentStartDateTime;

    @Transient
    private String concatFields;


    @ManyToOne
    @JoinColumn(name = "poker_room_id")
    private PokerRoom pokerRoom;

    public String getConcatFields() {
        return name + " " + type + " " + speed;


    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        TournamentGlobal that = (TournamentGlobal) o;
//        return getId() != null && Objects.equals(getId(), that.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractTournament that = (AbstractTournament) o;
        return Id != null && Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}