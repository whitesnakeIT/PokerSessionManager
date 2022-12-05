package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoom;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PokerRoomRepository extends JpaRepository<PokerRoom, Long> {
    @Query(value = "select * from poker_rooms where user_id = (:userId)"
            , nativeQuery = true)
    List<PokerRoom> findPokerRoomsByUserId(@Param("userId") Long userId);
}
