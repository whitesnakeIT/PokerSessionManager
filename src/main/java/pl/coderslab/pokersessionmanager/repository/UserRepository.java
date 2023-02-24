package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.user.User;

//import javax.transaction.Transactional;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Email @NotNull @NotEmpty String email);

    @Modifying
    @Query(value = "update users set first_name = (:firstName), last_name = (:lastName), username = (:username) where id = (:user)", nativeQuery = true)
    void update(@Param("user") User user, @Param("firstName")  String firstName, @Param("lastName")  String lastName,@Param("username")  String username);

//    @Override
//    @Query(value =
//            "select *" +
//                    "from users u " +
//                    "left join tournaments t " +
//                    "on u.id = t.player_id where u.id = (:userId)"
//            ,nativeQuery = true)
//    Optional<User> findById(@Param("userId") Long userId);
}
