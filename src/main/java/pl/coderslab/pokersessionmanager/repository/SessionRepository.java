package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Session;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from poker_session_manager.sessions where user_id = :userId", nativeQuery = true)
    List<Session> findAllUserSessions(@Param(value = "userId") Long userId);

}
