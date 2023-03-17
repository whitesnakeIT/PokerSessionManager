package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Session;

import java.util.List;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from sessions where player_id = :playerId", nativeQuery = true)
    List<Session> findSessionsByPlayerId(@Param(value = "playerId") Long playerId);

}
