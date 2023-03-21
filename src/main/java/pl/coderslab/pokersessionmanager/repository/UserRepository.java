package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
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
    Optional<User> findByEmail(String email);

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
