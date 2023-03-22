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

@SpringBootTest
class PokerRoomServiceTest extends Specification {

    def private static final PLAYER_ID = 1L
    def private static final POKER_ROOM_ID = 1L


    def private final pokerRoomRepository = Mock(PokerRoomRepository.class)
    def private final userService = Mock(UserService.class)
    def private final pokerRoomMapper = Mock(PokerRoomMapper.class)

    def private final pokerRoomService = new PokerRoomService(pokerRoomRepository, userService, pokerRoomMapper)

    def private final mockedPokerRoomService = Mock(PokerRoomService.class)

    def private final mockedPlayer() {
        def player = new Player()
        player.id = 1

        player
    }

    def private final mockedPokerRoom() {
        def pokerRoom = new PokerRoom()
        pokerRoom.id = 1L
        pokerRoom.name = "testPokerRoom"
        pokerRoom.url = "https://www.testPokerRoom.com"
        pokerRoom.pokerRoomScope = PokerRoomScope.GLOBAL
        pokerRoom.player = mockedPlayer()

        pokerRoom
    }

    def private final mockedPokerRoomSlimWithOnlyId() {
        def pokerRoomSlim = new PokerRoomSlim()
        pokerRoomSlim.id = 1

        pokerRoomSlim
    }

    def private final mockedPokerRoomList() {
        [new PokerRoom(), new PokerRoom()]
    }

    def private final stubsForFindByIdRepositoryMethod() {
        pokerRoomRepository.findById(_ as Long) >> Optional.of(mockedPokerRoom())
        userService.getLoggedUser() >> mockedPlayer()
        userService.isLoggedAsAdmin() >> true
    }

    def """should check if service method create(PokerRoom pokerRoom)
 is invoking method save(PokerRoom pokerRoom) from repository with the same object exactly once"""() {
        given:
        userService.isLoggedAsUser() >> true
        def pokerRoom = new PokerRoom()

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
        userService.isLoggedAsUser() >> true
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
        def pokerRoomScopeByRole = pokerRoomService.getScopeByRole()

        then:
        pokerRoomScopeByRole == PokerRoomScope.GLOBAL
    }

    def """should check if service method setPokerRoomScopeByRole()
 is setting PokerRoomScope.LOCAL when basic user is logged"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        userService.isLoggedAsUser()
        def pokerRoomScopeByRole = pokerRoomService.getScopeByRole()

        then:
        pokerRoomScopeByRole == PokerRoomScope.LOCAL
    }

    def """should check if method is throwing an exception when user authority
 not contains ROLE_USER or ROLE_ADMIN"""() {
        given:
        !userService.isLoggedAsAdmin() || !userService.isLoggedAsUser() >> false

        when:
        pokerRoomService.getScopeByRole()

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting poker room scope by role failed. Unrecognized logged user role."
    }

    def """for service method edit(PokerRoomSlim pokerRoomSlim) should first update entity from DTO
 and then execute repository method save(PokerRoom pokerRoom) for converter object exactly once"""() {
        given:
        stubsForFindByIdRepositoryMethod()
        pokerRoomMapper.updatePokerRoomFromPokerRoomSlim(_ as PokerRoomSlim, _ as PokerRoom) >> mockedPokerRoom()
        def pokerRoomSlim = mockedPokerRoomSlimWithOnlyId()

        when:
        pokerRoomService.edit(pokerRoomSlim)

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
                .checkIfBelongsToUser(mockedPokerRoom(), mockedPlayer())

        then:
        result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
 is returning false when basic user is logged but poker room not belongs to him"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = pokerRoomService
                .checkIfBelongsToUser(mockedPokerRoom(), new Player())

        then:
        !result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
 is returning false when basic user is logged but poker room hasn't owner"""() {
        given:
        userService.isLoggedAsUser() >> true

        when:
        def result = pokerRoomService
                .checkIfBelongsToUser(new PokerRoom(), new Player())

        then:
        !result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
 is returning true when administrator is logged"""() {
        given:
        userService.isLoggedAsAdmin() >> true

        when: "Administrators can edit or delete all poker rooms."
        def result = pokerRoomService
                .checkIfBelongsToUser(mockedPokerRoom(), new Admin())

        then:
        result
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
 is throwing an exception when User type parameter is null"""() {
        when:
        pokerRoomService
                .checkIfBelongsToUser(new PokerRoom(), null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if poker room belongs to user failed. User is null."
    }

    def """should check if service method checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user)
 is throwing an exception when PokerRoom type parameter is null"""() {
        when:
        pokerRoomService
                .checkIfBelongsToUser(null, new User())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if poker room belongs to user failed. Poker room is null."
    }

    def """should check if service method delete(Long pokerRoomId) is invoking
 method delete(Long pokerRoomId) from repository on the same object exactly once"""() {
        given:
        stubsForFindByIdRepositoryMethod()
        def pokerRoom = mockedPokerRoom()

        when:
        pokerRoomService.delete(POKER_ROOM_ID)

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
        given:
        stubsForFindByIdRepositoryMethod()

        when:
        def pokerRoom = pokerRoomService.findById(POKER_ROOM_ID)

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

    def """should check if service method findById(Long pokerRoomId) is throwing an exception
 when pokerRoom belongs to logged user"""() {
        given: "need to prepare other player with other poker room which belongs to him"
        def pokerRoomNotBelongsToUser = new PokerRoom()
        pokerRoomNotBelongsToUser.id = 2

        def otherOwner = new Player()
        pokerRoomNotBelongsToUser.player = otherOwner

        and: "need to stub methods for repository method findById(Long pokerRoomId)"
        userService.isLoggedAsUser() >> true
        pokerRoomRepository.findById(_ as Long) >> Optional.of(pokerRoomNotBelongsToUser)
        userService.getLoggedUser() >> mockedPlayer()

        when:
        pokerRoomService.findById(pokerRoomNotBelongsToUser.id)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for poker room failed. No permission to processing with this poker room."
    }

    def """should check if service method findGlobalPokerRooms() is returning
 correct size of the list of type PokerRoom"""() {
        given:
        pokerRoomRepository.findGlobalPokerRooms() >> mockedPokerRoomList()

        when:
        def pokerRoomList = pokerRoomService.findGlobalPokerRooms()

        then:
        verifyAll {
            pokerRoomList.size() == 2
            pokerRoomList.every { it instanceof PokerRoom }
        }
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
        userService.getLoggedUserId() >> PLAYER_ID
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
        userService.getLoggedUserId() >> PLAYER_ID
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
        userService.getLoggedUserId() >> PLAYER_ID

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
        when: "how to declare unrecognized enum? dead code to remove"
        PokerRoomScope
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
        userService.getLoggedUserId() >> PLAYER_ID
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
        stubsForFindByIdRepositoryMethod()
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

    def """should check if service method setPokerRoomDetails(PokerRoom pokerRoom) is correctly
 setting details when user is logged as administrator"""() {
        given:
        userService.isLoggedAsAdmin() >> true
        def pokerRoom = new PokerRoom()

        when:
        pokerRoomService.setPokerRoomDetails(pokerRoom)

        then: "when logged as administrator, poker room didn't have owner"
        verifyAll(pokerRoom) {
            pokerRoom.pokerRoomScope == PokerRoomScope.GLOBAL
            pokerRoom.player == null
        }
    }

    def """should check if service method setPokerRoomDetails(PokerRoom pokerRoom) is correctly
 setting details when user is logged as basic user"""() {
        given:
        userService.isLoggedAsUser() >> true
        userService.getLoggedUser() >> new Player()
        def pokerRoom = new PokerRoom()

        when:
        pokerRoomService.setPokerRoomDetails(pokerRoom)

        then: "when logged as administrator, poker room have owner"
        verifyAll(pokerRoom) {
            pokerRoom.pokerRoomScope == PokerRoomScope.LOCAL
            pokerRoom.player != null
        }
    }

    def """should check if service method setPokerRoomDetails(PokerRoom pokerRoom) is throwing
 an exception when poker room is null"""() {
        when:
        pokerRoomService.setPokerRoomDetails(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Setting poker room details failed. Poker room is null."


    }
}



