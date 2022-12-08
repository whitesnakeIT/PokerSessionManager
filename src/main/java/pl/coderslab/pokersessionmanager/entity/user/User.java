package pl.coderslab.pokersessionmanager.entity.user;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.validator.Adult;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = User.TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "users";

    @Id
    @Column
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

    //    @NotNull  admin nie moze miec balansu

    private int enabled;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favourite_tournaments",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tournament_id", referencedColumnName = "id")}
    )
    private List<AbstractTournament> favouriteTournaments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<AbstractTournament> suggestedTournaments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<AbstractTournament> localTournaments = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Session> sessions;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserStats userStats;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<PokerRoom> pokerRoomsLocal;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


    public boolean hasRole(String roleName){
        for (Role role : this.roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
