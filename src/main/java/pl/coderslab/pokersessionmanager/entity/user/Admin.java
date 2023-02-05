package pl.coderslab.pokersessionmanager.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

@Entity
//@Table(name = Admin.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@DiscriminatorValue(Admin.USER_TYPE)
public class Admin extends User {

//        public static final String TABLE_NAME = "admins";

    public static final String USER_TYPE = "admin";

}
