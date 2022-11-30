package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

//@Entity
@Data
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

    public String getConcatFields() {
        return name + " " + type + " " + speed;


    }
}
