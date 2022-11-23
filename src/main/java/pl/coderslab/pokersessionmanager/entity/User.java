package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.pokersessionmanager.validator.Adult;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = User.TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "users";
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favourite_tournaments",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id")}
    )
    List<Tournament> favouriteTournaments;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Session> sessions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Transient
    private String fullName;

    @Email
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private LocalDateTime created;

    @NotNull
    @Adult
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    private boolean superAdmin;

    //    @NotNull  admin nie moze miec balansu
    private double balance;

    //
//@OneToOne(cascade = CascadeType.REMOVE)
//@JoinColumn(name = "user_stats_id")
//    private UserStats usersStats;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


}
