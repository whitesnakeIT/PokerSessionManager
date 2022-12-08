package pl.coderslab.pokersessionmanager.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = UserStats.TABLE_NAME)
public class UserStats {

    public static final String TABLE_NAME = "user_stats";

    @Id
    @Column(name = "user_id")
    private Long id;

    private double balance;


    private int tournamentCount;


    private int tournamentWins;


    private double profit;


    private double averageProfit;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
