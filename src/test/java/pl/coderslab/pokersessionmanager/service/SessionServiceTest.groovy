package pl.coderslab.pokersessionmanager.service

import org.hibernate.Hibernate
import pl.coderslab.pokersessionmanager.entity.Session
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal
import pl.coderslab.pokersessionmanager.entity.user.Admin
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.entity.user.User
import pl.coderslab.pokersessionmanager.repository.SessionRepository
import spock.lang.Ignore
import spock.lang.Specification

class SessionServiceTest extends Specification {

    def private static final PLAYER_ID = 1L
    def private static final SESSION_ID = 1L

    def private final sessionRepository = Mock(SessionRepository)

    def private final userService = Mock(UserService)

    def private final sessionService = new SessionService(sessionRepository, userService)

    def mockedPlayer() {
        def player = new Player()
        player.id = 1L

        player
    }

    def mockedSession() {
        def session = new Session()
        session.id = 1L
        session.name = "testSession"
        session.player = mockedPlayer()
        session.sessionTournaments = mockedTournamentsForSession()

        session
    }

    def mockedTournamentsForSession() {
        def testTournamentLocal = new TournamentLocal()
        testTournamentLocal.setBuyIn(30)
        def testTournamentGlobal = new TournamentGlobal()
        testTournamentGlobal.setBuyIn(70)

        [testTournamentLocal, testTournamentGlobal]
    }

    void stubsForFindByIdRepositoryMethod() {
        sessionRepository.findById(_ as Long) >> Optional.of(mockedSession())
        userService.getLoggedUser() >> new Player()
        userService.isLoggedAsAdmin() >> true
    }

    def """should check if service method create(Session session) is invoking
 repository method save(Session session) with the same object exactly once"""() {
        given:
        userService.isLoggedAsUser() >> true
        userService.getLoggedUser() >> new Player()

        when:
        sessionService.create(mockedSession())

        then:
        1 * sessionRepository.save(mockedSession())
    }

    def """should check if service method create(Session session) is throwing
 an exception when session is null"""() {
        when:
        sessionService.create(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Creating session failed. Session is null."
    }

    def """should check if service method findAllPlayerSessions(Long playerId) is returning
 correct size of the list"""() {
        given:
        sessionRepository.findSessionsByPlayerId(_ as Long) >> mockedTournamentsForSession()

        when:
        def sessionsByPlayerId = sessionService.findSessionsByPlayerId(PLAYER_ID)

        then:
        sessionsByPlayerId.size() == 2
    }

    def """should check if service method findAllPlayerSessions(Long playerId) is throwing
 an exception when playerId is null"""() {
        when:
        sessionService.findSessionsByPlayerId(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching all player sessions failed. Player id is null"
    }

    def """should check if service method findById(Long sessionId) is returning
 session entity object based on the sessionId"""() {
        given:
        stubsForFindByIdRepositoryMethod()

        when:
        def session = sessionService.findById(SESSION_ID)

        then:
        session.name == "testSession"
    }

    def """should check if service method findById(Long sessionId) is throwing
 an exception when sessionId is null"""() {
        when:
        sessionService.findById(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for session failed. Session id is null."
    }

    def """should check if service method delete(Long sessionId) is invoking
 method delete(Long sessionId) from repository on the same object exactly once"""() {
        given:
        stubsForFindByIdRepositoryMethod()

        when:
        sessionService.delete(SESSION_ID)

        then:
        1 * sessionRepository.delete(mockedSession())
    }

    def """should check if service method delete(Long sessionId) is throwing
 an exception when sessionId is null"""() {
        when:
        sessionService.delete(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Deleting session failed. Session id is null."
    }

    def """should check if service method loadTournamentsToSession(Session session) is correctly
 initializing list od tournaments in session"""() {
        given:
        def session = mockedSession()

        when:
        sessionService.loadTournamentsToSession(session)
        def isInitialized = Hibernate.isInitialized(session.sessionTournaments)

        then:
        isInitialized
    }

    def """should check if service method loadTournamentsToSession(Session session) is throwing
 an exception when session is null"""() {
        when:
        sessionService.loadTournamentsToSession(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Loading tournaments to session failed. Session is null."

    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is returning true when basic user is logged and session belongs to him"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = sessionService
                .checkIfSessionBelongsToUser(mockedSession(), mockedPlayer())

        then:
        result
    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is returning false when basic user is logged but session not belongs to him"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = sessionService
                .checkIfSessionBelongsToUser(mockedSession(), new User())

        then:
        !result
    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is throwing an exception when session not have owner"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        sessionService.checkIfSessionBelongsToUser(new Session(), mockedPlayer())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if session belongs to user failed. Session don't have owner."
    }

    def """should check if service method findById(Long sessionId) is throwing
 an exception when basic user is logged and session not belongs to him"""() {
        given: "need to prepare other player with other session which belongs to him"
        def sessionNotBelongsToPlayer = new Session()
        sessionNotBelongsToPlayer.id = 2

        def otherOwner = new Player()
        sessionNotBelongsToPlayer.player = otherOwner

        and: "need to stub methods for repository method findById(Long sessionId)"
        userService.isLoggedAsUser() >> true
        sessionRepository.findById(_ as Long) >> Optional.of(sessionNotBelongsToPlayer)
        userService.getLoggedUser() >> mockedPlayer()

        when:
        sessionService.
                findById(sessionNotBelongsToPlayer.id)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for session failed. No permission to processing with this session."

    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is returning true when administrator is logged"""() {
        given:
        userService.isLoggedAsAdmin() >> true

        when: "Administrators can edit or delete all sessions."
        def result = sessionService
                .checkIfSessionBelongsToUser(mockedSession(), new Admin())

        then:
        result
    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is throwing an exception when User type parameter is null"""() {
        when:
        sessionService
                .checkIfSessionBelongsToUser(new Session(), null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if session belongs to user failed. User is null."
    }

    def """should check if service method checkIfSessionBelongsToUser(Session session, User user)
 is throwing an exception when Session type parameter is null"""() {
        when:
        sessionService
                .checkIfSessionBelongsToUser(null, new User())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if session belongs to user failed. Session is null."
    }

    def """should check if service method edit(Session session) is invoking
 repository method save on the same object exactly once"""() {
        when:
        sessionService.edit(mockedSession())

        then:
        1 * sessionRepository.save(mockedSession())
    }

    def """should check if service method edit(Session session) is throwing
 an exception when session is null"""() {
        when:
        sessionService.edit(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Editing session failed. Session id is null."
    }

    @Ignore("method is private")
    def """should check if service method setOwner(Session session) is throwing
 an exception when session is null"""() {

        when:
        sessionService.setOwner(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting session owner failed. Session is null"
    }

    @Ignore("method is private")
    def """should check if service method setOwner(Session session) is throwing
 an exception when admin is logged"""() {
        given:
        userService.isLoggedAsAdmin()

        when:
        sessionService.setOwner(mockedSession())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting owner for session failed. Owner must have Player class."

    }

    @Ignore("method is private")
    def """should check if service method countTotalSessionCost(Session session) is returning
 correct amount"""() {
        given:
        def session = mockedSession()

        when:
        sessionService.countTotalSessionCost(session)

        then:
        session.totalCost > 0
    }

    @Ignore("method is private")
    def """should check if service method countTotalSessionCost(Session session) is throwing
 an exception when session is null"""() {

        when:
        sessionService.countTotalSessionCost(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Counting total session cost failed. Session is null."
    }

    @Ignore("method is private")
    def """should check if service method setSessionDetails(Session session) is setting
 correctly session details"""() {
        given:
        def session = mockedSession()

        when:
        sessionService.setSessionDetails(session)

        then:
        verifyAll(session) {
//            getTournamentCount() > 0
//            getTotalCost() > 0
        }
    }

    @Ignore("method is private")
    def """should check if service method setSessionDetails(Session session) is throwing
 an exception when session is null"""() {
        when:
        sessionService.setSessionDetails(null)


        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting session details failed. Session is null."
    }

}

