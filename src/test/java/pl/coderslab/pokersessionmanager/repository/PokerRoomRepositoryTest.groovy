package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class PokerRoomRepositoryTest extends Specification {

    @Autowired
    private PokerRoomRepository pokerRoomRepository;

    private static final PLAYER_ID = 1L;


    def "should return amount of poker rooms by user id"() {
        when:
        def playerPokerRooms = pokerRoomRepository.findPokerRoomsByPlayerId(PLAYER_ID)

        then:
        playerPokerRooms.size() == 1
    }

    def "should return amount of poker rooms global (without owner), available for every player"() {
        when:
        def globalPokerRooms = pokerRoomRepository.findGlobalPokerRooms()

        then:
        globalPokerRooms.size() == 2;
    }
}
