package pl.coderslab.pokersessionmanager.repository;

import jakarta.annotation.security.PermitAll;
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

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @PermitAll
    Optional<User> findByEmail(@Email @NotNull @NotEmpty String email) throws Exception;

    @Modifying(clearAutomatically = true)
    @Query(value = "update users set first_name = :firstName," +
            " last_name = :lastName," +
            " username = :username" +
            " where id = :userId"
            , nativeQuery = true)
    void updateUserDetails(@Param("userId") Long userId,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("username") String username);

}
