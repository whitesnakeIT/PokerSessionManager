package pl.coderslab.pokersessionmanager.service

import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.PokerRoom
import pl.coderslab.pokersessionmanager.entity.Session
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope
import pl.coderslab.pokersessionmanager.enums.RoleName
import spock.lang.Specification

import static pl.coderslab.pokersessionmanager.enums.RoleName.*

@SpringBootTest
class RedirectServiceTest extends Specification {

    def private static final PLAYER_ID = 1L
    def private static final POKER_ROOM_ID = 1L
    def private static final SESSION_ID = 1L

    def private final userService = Mock(UserService)
    def private final pokerRoomService = Mock(PokerRoomService)
    def private final sessionService = Mock(SessionService)

    private final def mockedPokerRoom() {
        def pokerRoom = new PokerRoom()
        pokerRoom.id = POKER_ROOM_ID
        pokerRoom.pokerRoomScope = PokerRoomScope.LOCAL
        pokerRoom.player = mockedPlayer()
        pokerRoom
    }

    private final def mockedPlayer() {
        def player = new Player()
        player.id = PLAYER_ID

        player
    }

    private final def mockedSession() {
        def session = new Session()
        session.id = SESSION_ID
        session.player = mockedPlayer()
        session
    }

    def private final redirectService =
            new RedirectService(userService, pokerRoomService, sessionService)

    def """should check if service method getRedirectEmptyUrl() is correctly
 setting url value based on '#authority'"""() {
        given:
        userService.hasAuthority(authority as RoleName) >> true

        when:
        def url = redirectService.getRedirectEmptyUrl()

        then:
        url == result

        where:
        authority      | result
        ROLE_ADMIN     | "redirect:/app/admin/dashboard"
        ROLE_USER      | "redirect:/app/player/dashboard"
        ROLE_ANONYMOUS | "redirect:/login"
    }

    def """should check if service method getRedirectEmptyUrl() is throwing
 an exception when authority is unrecognized'"""() {
        when:
        redirectService.getRedirectEmptyUrl()

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Getting redirect url on / address failed. Unrecognized authority."
    }

    def """should check if service method getRedirectEmptyUrl() is correctly
 setting url value for un logged users on /login page"""() {
        given:
        userService.hasAuthority(ROLE_ANONYMOUS) >> true

        when:
        def url = redirectService.getRedirectLoginPage()

        then:
        url == "authorization/login/loginForm"
    }

    def """should check if service method getRedirectEmptyUrl() is correctly
 setting url value on /login page based on '#isLogged'"""() {
        given:
        userService.hasAuthority(ROLE_ANONYMOUS) >> isLogged

        when:
        def url = redirectService.getRedirectLoginPage()

        then:
        url == result

        where:
        isLogged | result
        true     | "authorization/login/loginForm"
        false    | "redirect:/"
    }

    def """should check if service method getRedirectForPokerRoomListByRole() is correctly
 setting url value based on scope '#pokerRoomScope'"""() {
        given:
        pokerRoomService.getScopeByRole() >> pokerRoomScope

        when:
        def url = redirectService.getRedirectForPokerRoomListByRole()

        then:
        url == String.format("redirect:/app/poker_room/%s/all", pokerRoomScope)

        where:
        pokerRoomScope        | _
        PokerRoomScope.GLOBAL | _
        PokerRoomScope.LOCAL  | _

    }
//    def """should check if service method getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId)
// is correctly setting url when pokerRoom hasn't owner"""() {
//
//        given: "poker room without owner has scope global, it means that administrator is logged"
//        pokerRoomService.hasOwner(_ as PokerRoom) >> false
//
//        when:
//        def url = redirectService.getRedirectAfterProcessingPokerRoomSlim(1L)
//
//        then: "returning administrator endpoint"
//        url == "redirect:/app/poker_room/global/all"
//    }
//    def """should check if service method getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId)
// is correctly setting url when pokerRoom has owner and player is logged"""() {
//
//        given:"poker room with owner has scope local, it means that player is logged"
//        userService.hasAuthority(ROLE_USER) >> true
//
//        when:
//        def url = redirectService.getRedirectAfterProcessingPokerRoomSlim(1L)
//
//        then:"returning player endpoint"
//        url == "redirect:/app/poker_room/local/all"
//
//    }

    def """should check if service method getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId)
 is correctly setting url when administrator is processing with player pokerRoom"""() {
        given:
        userService.hasAuthority(ROLE_ADMIN) >> true
        pokerRoomService.hasOwner(_ as PokerRoom) >> true
        pokerRoomService.findById(_ as Long) >> mockedPokerRoom()

        when:
        def url = redirectService.getRedirectAfterProcessingPokerRoomSlim(POKER_ROOM_ID)

        then:
        url == String.format("redirect:/app/admin/users/details/%d", PLAYER_ID)
    }

    def """should check if service method getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId)
 is correctly setting url when administrator or player is processing with own pokerRoom"""() {
        given:
        pokerRoomService.findById(_ as Long) >> mockedPokerRoom()
        userService.hasAuthority(_ as RoleName) >> true

        when:
        def pokerRoom = pokerRoomService.findById(POKER_ROOM_ID)
        def url = redirectService.getRedirectAfterProcessingPokerRoomSlim(pokerRoom.id)

        then:
        url == String.format("redirect:/app/poker_room/%s/all", pokerRoom.pokerRoomScope)
    }

    def """should check if service method getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId)
 is throwing an exception when pokerRoomSlimId is null"""() {
        when:
        redirectService.getRedirectAfterProcessingPokerRoomSlim(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Getting poker room redirect url failed. Poker room slim id is null."
    }

    def """should check if service method getRedirectAfterProcessingSession(Long sessionId)
 is correctly returning url based on '#role"""() {
        given:
        sessionService.findById(_ as Long) >> mockedSession()
        userService.hasAuthority(role) >> true

        when:
        def url = redirectService.getRedirectAfterProcessingSession(SESSION_ID)

        then:
        url == result

        where:
        role       | result
        ROLE_USER  | "redirect:/app/session/all"
        ROLE_ADMIN | String.format("redirect:/app/admin/users/details/%d", PLAYER_ID)
    }

    def """should check if service method getRedirectAfterProcessingSession(Long sessionId)
 is throwing an exception when sessionId is null"""() {
        when:
        redirectService.getRedirectAfterProcessingSession(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting session redirect url failed. Session id is null."
    }

    def """should check if service method getRedirectAfterProcessingSession(Long sessionId)
 is throwing an exception when authority is not recognized"""() {
        when:
        redirectService.getRedirectAfterProcessingSession(SESSION_ID)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting session redirect url failed. Authority is not recognized"
    }
}