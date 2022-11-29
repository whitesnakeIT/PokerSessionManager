package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Session;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from poker_session_manager.sessions where user_id = :userId", nativeQuery = true)
    List<Session> findAllUserSessions(@Param( value = "userId") Long userId);
//    @Query("select s from  User s where s.s")
//    List <Session> findAllByUserId(Long userId);
//
//    @Query(value = "select * from sessions where user_id = (:userId)",nativeQuery = true)
//    List<Session> findAllUserSession(@Param(value = "userId") Long userId);
////
//    Optional<Session> findFirstByUserIdAndName(Long userId, String sessionName);

//    @Modifying
//    @Query(value = "insert into sessions (id, name, user_id) values ((:sessionId),(:sessionName),(:userId))", nativeQuery = true)
//    void addNewSession(@Param(value = "sessionId") Long sessionId, @Param(value = "sessionName") String sessionName, @Param(value = "userId") Long userId);
//
//    @Modifying
//    @Query(value = "update sessions set name=:sessionName where id=(:sessionId)", nativeQuery = true)
//    void editSession(@Param(value = "sessionName") String sessionName, @Param(value = "sessionId") Long sessionId);
}
