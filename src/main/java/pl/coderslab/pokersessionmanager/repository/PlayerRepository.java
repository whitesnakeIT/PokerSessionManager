package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.user.Player;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player, Long> {


}
