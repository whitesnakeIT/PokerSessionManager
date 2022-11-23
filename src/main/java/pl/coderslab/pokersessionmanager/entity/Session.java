package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = Session.TABLE_NAME)
public class Session {

    public static final String TABLE_NAME = "sessions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
        name = "sessions_tournaments",
        joinColumns = {@JoinColumn (name = "session_id")},
        inverseJoinColumns = {@JoinColumn (name = "tournament_id")}
)
    List<Tournament> sessionTournaments;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

}
