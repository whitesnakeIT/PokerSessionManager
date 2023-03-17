package pl.coderslab.pokersessionmanager.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = PlayerStats.TABLE_NAME)
public class PlayerStats {

    public static final String TABLE_NAME = "player_stats";

    @Id
    @Column(name = "player_id")
    private Long id;

    private double balance;


    private int tournamentCount;


    private int tournamentWins;


    private double profit;


    private double averageProfit;


    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;
}
