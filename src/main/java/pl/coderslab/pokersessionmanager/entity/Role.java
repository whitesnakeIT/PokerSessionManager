package pl.coderslab.pokersessionmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.coderslab.pokersessionmanager.converter.RoleNameEnumToColumnConverter;
import pl.coderslab.pokersessionmanager.enums.RoleName;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = Role.TABLE_NAME)
public class Role {
    public static final String TABLE_NAME = "roles" ;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = RoleNameEnumToColumnConverter.class)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
