package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.user.Player;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player, Long> {


}
