package pl.coderslab.pokersessionmanager.service

import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.PokerRoom
import pl.coderslab.pokersessionmanager.entity.user.Admin
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.entity.user.User
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope
import pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room.PokerRoomSlim
import pl.coderslab.pokersessionmanager.mapstruct.mappers.PokerRoomMapper
import pl.coderslab.pokersessionmanager.repository.PokerRoomRepository
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PokerRoomServiceTest extends Specification {

    def pokerRoomRepository = Mock(PokerRoomRepository.class)
    def userService = Mock(UserService.class)
    def pokerRoomMapper = Mock(PokerRoomMapper.class)

    def pokerRoomService = new PokerRoomService(pokerRoomRepository, userService, pokerRoomMapper)

    def mockedPokerRoomService = Mock(PokerRoomService.class)

    def mockedPlayer() {
        def player = new Player()
        player.id = 1

        player
    }

    def private static final PLAYER_ID = 1L
    def private static final POKER_ROOM_ID = 1L

    def mockedPokerRoom() {
        def pokerRoom = new PokerRoom()
        pokerRoom.id = 1L
        pokerRoom.name = "testPokerRoom"
        pokerRoom.url = "https://www.testPokerRoom.com"
        pokerRoom.pokerRoomScope = PokerRoomScope.GLOBAL
        pokerRoom.player = mockedPlayer()

        pokerRoom
    }

    private mockedPokerRoomSlimWithOnlyId() {
        def pokerRoomSlim = new PokerRoomSlim()
        pokerRoomSlim.id = 1

        pokerRoomSlim
    }

    def mockedPokerRoomList() {
        [new PokerRoom(), new PokerRoom()]
    }

    def """should check if service method create(PokerRoom pokerRoom)
is invoking method save(PokerRoom pokerRoom) from repository with the same object exactly once"""() {
        given:
        def pokerRoom = new PokerRoom()
        pokerRoomRepository.save(_ as PokerRoom) >> mockedPokerRoom()

        when:
        pokerRoomService.create(pokerRoom)

        then:
        1 * pokerRoomRepository.save(pokerRoom)
    }

    def """should check if service method create(PokerRoom pokerRoom)
is throwing an exception when pokerRoom is null"""() {
        when:
        pokerRoomService.create(null as PokerRoom)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Creating poker room failed. Poker room is null."
    }

    def """for service method create(PokerRoomSlim pokerRoomSlim) should first converter DTO to Entity
 and then execute repository method save(PokerRoom pokerRoom) for converted object exactly once"""() {
        given:
        pokerRoomMapper.pokerRoomSlimToPokerRoom(_ as PokerRoomSlim) >> mockedPokerRoom()

        when:
        pokerRoomService.create(mockedPokerRoomSlimWithOnlyId())

        then:
        1 * pokerRoomRepository.save(mockedPokerRoom())
    }

    def """should check if service method create(PokerRoomSlim pokerRoomSlim)
is throwing an exception when pokerRoomSlim is null"""() {
        when:
        pokerRoomService.create(null as PokerRoomSlim)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Creating poker room slim failed. Poker room slim is null."
    }


    def """should check if service method setPokerRoomScopeByRole()
is setting PokerRoomScope.GLOBAL when basic administrator is logged"""() {
        given:
        userService.isLoggedAsAdmin() >> true

        when:
        userService.isLoggedAsAdmin()
        def pokerRoomScopeByRole = pokerRoomService.setPokerRoomScopeByRole()

        then:
        pokerRoomScopeByRole == PokerRoomScope.GLOBAL
    }

    def """should check if service method setPokerRoomScopeByRole()
is setting PokerRoomScope.LOCAL when basic user is logged"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        userService.isLoggedAsUser()
        def pokerRoomScopeByRole = pokerRoomService.setPokerRoomScopeByRole()

        then:
        pokerRoomScopeByRole == PokerRoomScope.LOCAL
    }

    def """should check if method is throwing an exception when user authority
 not contains ROLE_USER or ROLE_ADMIN"""() {
        given:
        !userService.isLoggedAsAdmin() || !userService.isLoggedAsUser() >> false

        when:
        userService.isLoggedAsUser()
        pokerRoomService.setPokerRoomScopeByRole()

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting poker room scope by role failed."
    }

    def """for service method edit(PokerRoomSlim pokerRoomSlim) should first update entity from DTO
 and then execute repository method save(PokerRoom pokerRoom) for converter object exactly once"""() {
        given:
        def pokerRoomSlim = mockedPokerRoomSlimWithOnlyId()

        when:
        pokerRoomService.edit(pokerRoomSlim)

        then: "need to mock few methods inside, these methods tested below"
        pokerRoomMapper.updatePokerRoomFromPokerRoomSlim(_ as PokerRoomSlim, _ as PokerRoom) >> mockedPokerRoom()
        pokerRoomRepository.findById(_ as Long) >> Optional.ofNullable(mockedPokerRoom())
        userService.getLoggedUser() >> new User()
        userService.isLoggedAsAdmin() >> true

        then:
        1 * pokerRoomRepository.save(mockedPokerRoom())
    }

    def """should check if service method edit(PokerRoomSlim pokerRoomSlim) is throwing an exception
when pokerRoomSlim is null"""() {
        when:
        pokerRoomService.edit(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Editing poker room failed. Poker room slim id is null."
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
is returning true when basic user is logged and poker room belongs to him"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = pokerRoomService
                .checkIfPokerRoomBelongsToUser(mockedPokerRoom(), mockedPlayer())

        then:
        result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
is returning false when basic user is logged and poker room not belongs to him"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = pokerRoomService
                .checkIfPokerRoomBelongsToUser(mockedPokerRoom(), new Player())

        then:
        !result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
is returning true when administrator is logged"""() {
        given:
        userService.isLoggedAsAdmin() >> true

        when: "Administrators can edit or delete all poker rooms."
        def result = pokerRoomService
                .checkIfPokerRoomBelongsToUser(mockedPokerRoom(), new Admin())

        then:
        result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
is throwing an exception when User type parameter is null"""() {
        when:
        pokerRoomService
                .checkIfPokerRoomBelongsToUser(new PokerRoom(), null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if poker room belongs to user failed. User is null."
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
is throwing an exception when PokerRoom type parameter is null"""() {
        when:
        pokerRoomService
                .checkIfPokerRoomBelongsToUser(null, new User())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if poker room belongs to user failed. Poker room is null."
    }

    def """should check if service method delete(Long pokerRoomId) is invoking
method delete(Long pokerRoomId) from repository on the same object"""() {
        given:
        def pokerRoom = mockedPokerRoom()

        when:
        pokerRoomService.delete(pokerRoom.getId())

        then:
        pokerRoomRepository.findById(_ as Long) >> Optional.ofNullable(pokerRoom)
        userService.getLoggedUser() >> new User()
        userService.isLoggedAsAdmin() >> true

        then:
        1 * pokerRoomRepository.delete(pokerRoom)
    }

    def """should check if service method delete(Long pokerRoomId) is throwing
an exception when pokerRoomId is null"""() {
        when:
        pokerRoomService.delete(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Deleting poker room failed. Poker room id is null."

    }

    def """should check if service method findPokerRoomsByUserId(Long userId) is returning
correct size of list"""() {
        given:
        pokerRoomRepository.findPokerRoomsByPlayerId(PLAYER_ID) >> mockedPokerRoomList()

        when:
        def pokerRoomsByUserId = pokerRoomService.findPokerRoomsByPlayerId(PLAYER_ID)

        then:
        pokerRoomsByUserId.size() == 2
    }

    def """should check if service method findPokerRoomsByUserId(Long userId) is throwing
an exception when userId is null"""() {
        when:
        pokerRoomService.findPokerRoomsByPlayerId(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for user poker rooms failed. User id is null."
    }

    def """should check if service method findById(Long pokerRoomId) is returning
poker room entity object based on the pokerRoomId"""() {
        when:
        def pokerRoom = pokerRoomService.findById(POKER_ROOM_ID)

        then:
        pokerRoomRepository.findById(_ as Long) >> Optional.ofNullable(mockedPokerRoom())
        userService.getLoggedUser() >> new User()
        userService.isLoggedAsAdmin() >> true

        then:
        pokerRoom.name == "testPokerRoom"
    }

    def """should check if service method findById(Long pokerRoomId) is throwing an exception
when pokerRoomId is null"""() {
        when:
        pokerRoomService.findById(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker room failed. Poker room id is null."
    }


    def """should check if service method findGlobalPokerRooms() is returning
correct size of the list of type PokerRoom"""() {
        given:
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()

        when:
        def pokerRoomList = pokerRoomService.findGlobalPokerRooms()

        then:
        pokerRoomList.size() == 2
        pokerRoomList.every { it instanceof PokerRoom }
    }

    def """should check if service method findGlobalPokerRoomsSlim() is returning
correct size of the list of type PokerRoomSlim"""() {
        given:
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()
        pokerRoomMapper.pokerRoomToPokerRoomSlim(_ as PokerRoom) >> new PokerRoomSlim()

        when:
        def pokerRoomSlims = pokerRoomService.findGlobalPokerRoomsSlim()

        then:
        pokerRoomSlims.size() == 2
        pokerRoomSlims.every { it instanceof PokerRoomSlim }
    }

    def """should check if service method findAvailablePokerRoomForPlayer() is returning
correct size of the list of type PokerRoom"""() {
        given:
        userService.getLoggedUser() >> mockedPlayer()
        pokerRoomRepository.findPokerRoomsByPlayerId(_ as Long) >> mockedPokerRoomList()
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()

        when:
        def availablePokerRooms = pokerRoomService.findAvailablePokerRoomsForPlayer()

        then:
        availablePokerRooms.size() == 4
        availablePokerRooms.every { it instanceof PokerRoom }
    }

    def """should check if service method findAvailablePokerRoomForPlayer() is returning
correct size of the list of type PokerRoomSlim"""() {
        given:
        userService.getLoggedUser() >> mockedPlayer()
        pokerRoomRepository.findPokerRoomsByPlayerId(_ as Long) >> mockedPokerRoomList()
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()
        pokerRoomMapper.pokerRoomToPokerRoomSlim(_ as PokerRoom) >> new PokerRoomSlim()

        when:
        def availablePokerRoomsSlim = pokerRoomService.findAvailablePokerRoomsSlimForPlayer()

        then:
        availablePokerRoomsSlim.size() == 4
        availablePokerRoomsSlim.every { it instanceof PokerRoomSlim }

    }

    def """should check if service method findAllByScope(PokerRoomScope scope) is returning
correct list size of type PokerRoom based on '#scope'"""() {
        given:
        pokerRoomRepository.findPokerRoomsByPlayerId(_ as Long) >> mockedPokerRoomList()
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()
        userService.getLoggedUser() >> mockedPlayer()

        when:
        def pokerRoomsByScope = pokerRoomService.findAllByScope(scope)

        then:
        pokerRoomsByScope.size() == size
        pokerRoomsByScope.every { it instanceof PokerRoom }

        where:
        scope                 | size
        PokerRoomScope.LOCAL  | 2
        PokerRoomScope.GLOBAL | 2
    }

    def """should check if service method findAllByScope(PokerRoomScope scope) is throwing
an exception when scope is null"""() {
        when:
        pokerRoomService.findAllByScope(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker rooms failed. Poker room scope is null."
    }

    @Ignore
    def """should check if service method findAllByScope(PokerRoomScope scope) is throwing
an exception when scope is not recognized"""() {
        when: "how to declare unrecognized enum"
        pokerRoomService.findAllByScope(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker rooms failed. Unrecognized poker room scope: "
    }

    def """should check if service method findAllSlimByScope(PokerRoomScope scope) is returning
correct list size of type PokerRoomSlim based on '#scope'"""() {
        given:
        pokerRoomRepository.findPokerRoomsByPlayerId(_ as Long) >> mockedPokerRoomList()
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()
        userService.getLoggedUser() >> mockedPlayer()
        pokerRoomMapper.pokerRoomToPokerRoomSlim(_ as PokerRoom) >> new PokerRoomSlim()

        when:
        def pokerRoomsSlimByScope = pokerRoomService.findAllSlimByScope(scope)

        then:
        pokerRoomsSlimByScope.size() == size
        pokerRoomsSlimByScope.every { it instanceof PokerRoomSlim }

        where:
        scope                 | size
        PokerRoomScope.LOCAL  | 2
        PokerRoomScope.GLOBAL | 2
    }

    def """should check if service method findAllSlimByScope(PokerRoomScope scope) is throwing
an exception when scope is null"""() {
        when:
        pokerRoomService.findAllSlimByScope(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker rooms slim failed. Poker room slim scope is null."
    }

    def """should check if service method finSlimById(Long pokerRoomSlimId) is returning
poker room slim object based on the pokerRoomSlimId"""() {
        given:
        pokerRoomRepository.findById(_ as Long) >> Optional.ofNullable(mockedPokerRoom())
        userService.getLoggedUser() >> new User()
        userService.isLoggedAsAdmin() >> true
        pokerRoomMapper.pokerRoomToPokerRoomSlim(_ as PokerRoom) >> mockedPokerRoomSlimWithOnlyId()

        when:
        def pokerRoomSlimById = pokerRoomService.findSlimById(POKER_ROOM_ID)

        then:
        pokerRoomSlimById.id != null
    }

    def """should check if service method finSlimById(Long pokerRoomSlimId) is throwing
an exception because pokerRoomSlimId is null"""() {
        when:
        pokerRoomService.findSlimById(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker room slim failed. Poker room slim id is null."
    }


}
