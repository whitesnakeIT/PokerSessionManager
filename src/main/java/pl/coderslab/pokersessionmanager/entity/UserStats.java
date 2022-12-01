package pl.coderslab.pokersessionmanager.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserStats userStats = (UserStats) o;
        return Id != null && Objects.equals(Id, userStats.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
