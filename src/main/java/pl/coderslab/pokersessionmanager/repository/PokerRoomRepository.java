package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;

//import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PokerRoomRepository extends JpaRepository<PokerRoom, Long> {
    @Query(value = "select * from poker_rooms where player_id = (:playerId)"
            , nativeQuery = true)
    List<PokerRoom> findPokerRoomsByUserId(@Param("playerId") Long playerId);

    @Query(value = "select * from poker_rooms where poker_room_scope = 'global'",
            nativeQuery = true)
    List<PokerRoom> findGlobalPokerRooms();
}
