package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class SessionRepositoryTest extends Specification {

    @Autowired
    private SessionRepository sessionRepository

    private static final PLAYER_ID = 1L;


    def "should return all user sessions by user id"() {
        when:
        def sessionList = sessionRepository.findSessionsByPlayerId(PLAYER_ID)

        then:
        sessionList.size() == 1;
    }
}
