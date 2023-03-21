package pl.coderslab.pokersessionmanager.service

import org.hibernate.Hibernate
import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.repository.PlayerRepository
import spock.lang.Specification

@SpringBootTest
class PlayerServiceTest extends Specification {

    def private final static PLAYER_ID = 1L

    def private final playerRepository = Mock(PlayerRepository.class)
    def private final playerService = new PlayerService(playerRepository)

    def private final mockedPlayerList() {

        [new Player(), new Player()]
    }

    def private final mockedPlayer() {
        def player = new Player()
        player.id = PLAYER_ID
        player.firstName = "testPlayer"

        player
    }

    def """should check if service method findById(Long playerId) is returning
 player entity object based on the playerId"""() {
        given:
        playerRepository.findById(_ as Long) >> Optional.of(mockedPlayer())

        when:
        def player = playerService.findById(PLAYER_ID)

        then:
        player.firstName == "testPlayer"
    }

    def """should check if service method findById(Long playerId) is throwing
 an exception when playerId is null."""() {
        when:
        playerService.findById(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for player failed. Player id is null."
    }

    def """should check if service method findAllPlayers() is correctly
 returning list of type Player"""() {
        given:
        playerRepository.findAll() >> mockedPlayerList()

        when:
        def playerList = playerService.findAllPlayers()

        then:
        verifyAll {
            playerList.size() == 2
            playerList.every { it instanceof Player }
        }
    }
//
//    def """should check if service method ifPlayerExist(Optional<Player> optionalPlayer) is returning
// player object from function argument"""() {
//        given:
//        def optionalPlayer = Optional.of(mockedPlayer())
//
//        when:
//        def existingPlayer = playerService.ifPlayerExist(optionalPlayer)
//
//        then:
//        existingPlayer.id == PLAYER_ID
//    }
//
//    def """should check if service method ifPlayerExist(Optional<Player> optionalPlayer) is throwing
// an exception based on '#optionalParameter'"""() {
//        when:
//        playerService.ifPlayerExist(optionalParameter as Optional)
//
//        then:
//        def exception = thrown(RuntimeException)
//        exception.message == exceptedMessage
//
//        where:
//        optionalParameter | exceptedMessage
//        Optional.empty()  | "Searching for player failed. Player not exist."
//        null              | "Searching for player failed. Player is null."
//    }

    def """should check if service method loadLazyDataToPlayer(Player player) is initialing
 lazy collections to player correctly"""() {
        given:
        def player = mockedPlayer()

        when:
        def result = Hibernate.isInitialized(player.getFavouriteTournaments()) &&
                Hibernate.isInitialized(player.getSuggestedTournaments()) &&
                Hibernate.isInitialized(player.getLocalTournaments()) &&
                Hibernate.isInitialized(player.getPokerRoomsLocal()) &&
                Hibernate.isInitialized(player.getPlayerStats()) &&
                Hibernate.isInitialized(player.getSessions())

        then:
        result == true
    }
}
