package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
