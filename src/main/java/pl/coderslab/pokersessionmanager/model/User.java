package pl.coderslab.pokersessionmanager.model;

import lombok.Data;
import pl.coderslab.pokersessionmanager.entity.Tournament;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public  class User {


    private Long Id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
//    private LocalDateTime created;
//    private LocalDate birthdayDate;
    private  boolean superAdmin;



}
