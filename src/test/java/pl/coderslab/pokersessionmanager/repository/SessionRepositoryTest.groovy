package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class SessionRepositoryTest extends Specification {

    @Autowired
    private SessionRepository sessionRepository

    def private static final PLAYER_ID = 1L

    def """should check if repository method findSessionsByPlayerId(@Param(value = "playerId") Long playerId)
is correctly returning list of player sessions based on player id"""() {
        when:
        def sessionList = sessionRepository.findSessionsByPlayerId(PLAYER_ID)

        then:
        sessionList.size() == 1
        sessionList.every { it.player.id == PLAYER_ID }
    }
}
