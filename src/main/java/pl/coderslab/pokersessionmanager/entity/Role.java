package pl.coderslab.pokersessionmanager.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = Role.TABLE_NAME)
public class Role {
    public static final String TABLE_NAME = "roles" ;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

}
