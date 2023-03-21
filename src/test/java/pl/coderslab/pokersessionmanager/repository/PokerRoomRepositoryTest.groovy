package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class PokerRoomRepositoryTest extends Specification {

    @Autowired
    private PokerRoomRepository pokerRoomRepository

    def private static final PLAYER_ID = 1L

    def """should check if repository method findPokerRoomsByPlayerId(@Param("playerId") Long playerId)
is correctly returning list of player poker rooms based by player id"""() {
        when:
        def playerPokerRooms = pokerRoomRepository.findPokerRoomsByPlayerId(PLAYER_ID)

        then:
        playerPokerRooms.size() == 1
    }

    def """should check if repository method findGlobalPokerRooms()
is correctly returning list of type PokerRoom without owner"""() {
        when:
        def globalPokerRooms = pokerRoomRepository.findGlobalPokerRooms()

        then:
        globalPokerRooms.size() == 2
        globalPokerRooms.every { it.player == null }
    }
}
