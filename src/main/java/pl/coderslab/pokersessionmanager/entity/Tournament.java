package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = Tournament.TABLE_NAME)
public class Tournament {
    public static final String TABLE_NAME = "tournaments";

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
    @ManyToMany(mappedBy = "sessionTournaments")
    private List<Session> sessions;


}
