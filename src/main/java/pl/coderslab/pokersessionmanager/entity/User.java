package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.validator.Adult;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = User.TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Size(min = 5, max = 15)
    @Column(unique = true)
    private String username;

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

    private int enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favourite_tournaments",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id", referencedColumnName = "id")}
    )
    private List<TournamentGlobal> favouriteTournaments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id")
    private List<TournamentSuggestion> suggestedTournaments = new ArrayList<>();

//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private List<Session> sessions;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Session> sessions;

    @OneToOne(cascade = CascadeType.ALL)
    private UserStats userStats;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


}
