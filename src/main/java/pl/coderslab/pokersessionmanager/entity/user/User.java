package pl.coderslab.pokersessionmanager.entity.user;


//import jakarta.persistence.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.validator.Adult;

//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
//@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = User.USER_TYPE_COLUMN, discriminatorType = DiscriminatorType.STRING)
@Table(name = User.TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "users";
    public static final String USER_TYPE_COLUMN = "user_type";

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

    private int enabled;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles;


    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


    public boolean hasRole(RoleName roleName) {
        for (Role role : this.roles) {
            if (role.getName().equalsIgnoreCase(roleName.toString())) {
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
