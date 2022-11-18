package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = UserStats.TABLE_NAME)
public class UserStats {

    public static final String TABLE_NAME = "user_stats";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private int tournamentCount;


    private int tournamentWins;


    private double profit;


    private double averageProfit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;



}
