package pl.coderslab.pokersessionmanager.service


import org.hibernate.Hibernate
import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.PokerRoom
import pl.coderslab.pokersessionmanager.entity.Session
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.entity.user.PlayerStats
import pl.coderslab.pokersessionmanager.repository.PlayerRepository
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
class PlayerServiceTestSpock extends Specification {

    def playerRepository = Mock(PlayerRepository.class)
    def playerService = new PlayerService(playerRepository)
    def static playerId = 1L

    def prepareMockedPlayerList() {

        [new Player(), new Player()]
    }

    def prepareMockedPlayer() {
        def player = new Player()
        player.id = playerId

        player
    }

    def prepareLazyDataForPlayer(Player player) {
        player.sessions = List.of(new Session())
        player.suggestedTournaments = List.of(new TournamentSuggestion())
        player.localTournaments = List.of(new TournamentLocal())
        player.pokerRoomsLocal = List.of(new PokerRoom())
        player.favouriteTournaments = List.of(new TournamentGlobal(), new TournamentLocal())
        player.playerStats = new PlayerStats()

        player
    }

    def "should return size of player list"() {
        given:
        playerRepository.findAll() >> prepareMockedPlayerList()

        when:
        def playerList = playerService.findAllPlayers()

        then:
        playerList.size() == 2

    }

    def "should check if player exist and compare his Id"() {
        given:
        def optionalPlayer = Optional.of(prepareMockedPlayer())

        when:
        def existingPlayer = playerService.ifPlayerExist(optionalPlayer)

        then:
        existingPlayer.id == playerId
    }


    def "should check if player not exist based on '#optionalParameter' and throw exception"() {
        when:
        playerService.ifPlayerExist(optionalParameter)

        then:
        def exception = thrown(RuntimeException)
        exception.message == exceptedMessage

        where:
        optionalParameter | exceptedMessage
        Optional.empty()  | "Player not exist."
        null              | "Player is null."

    }

    // do poprawy
    @Ignore
    def "should check if all player lazy data is initialized correctly"() {
        given:
        def player = prepareLazyDataForPlayer(prepareMockedPlayer())
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
