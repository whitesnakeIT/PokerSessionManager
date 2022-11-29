package pl.coderslab.pokersessionmanager.model;

import lombok.Data;


@Data
public  class User {


    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
//    private LocalDateTime created;
//    private LocalDate birthdayDate;
    private  boolean superAdmin;



}
