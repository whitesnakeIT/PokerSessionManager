package pl.coderslab.pokersessionmanager.service

import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion
import pl.coderslab.pokersessionmanager.entity.user.Player
import pl.coderslab.pokersessionmanager.repository.TournamentRepository
import spock.lang.Specification

import static pl.coderslab.pokersessionmanager.enums.RoleName.ROLE_ADMIN
import static pl.coderslab.pokersessionmanager.enums.RoleName.ROLE_USER
import static pl.coderslab.pokersessionmanager.enums.TournamentScope.*

@SpringBootTest
class TournamentServiceTest extends Specification {

    def private final static TOURNAMENT_ID = 1L
    def private final static PLAYER_ID = 1L

    def private final tournamentRepository = Mock(TournamentRepository)
    def private final userService = Mock(UserService)
    def private final playerService = Mock(PlayerService)

    private TournamentService tournamentService =
            new TournamentService(tournamentRepository, userService, playerService)

    private final mockedGlobalTournament() {
        def tournamentGlobal = new TournamentGlobal()
        tournamentGlobal.id = 1L

        tournamentGlobal
    }

    private final mockedLocalTournament() {
        def tournamentLocal = new TournamentLocal()
        tournamentLocal.id = 2L

        tournamentLocal
    }

    private final mockedSuggestionTournament() {
        def tournamentSuggestion = new TournamentSuggestion()
        tournamentSuggestion.id = 3L

        tournamentSuggestion
    }

    private final mockedGlobalTournamentList() {
        def tournamentGlobal1 = new TournamentGlobal()
        tournamentGlobal1.id = 4L
        def tournamentGlobal2 = new TournamentGlobal()
        tournamentGlobal2.id = 5L
        [tournamentGlobal1, tournamentGlobal2]
    }

    private final mockedLocalTournamentList() {
        def tournamentLocal1 = new TournamentLocal()
        tournamentLocal1.id = 6L
        def tournamentLocal2 = new TournamentLocal()
        tournamentLocal2.id = 7L

        [tournamentLocal1, tournamentLocal2]
    }

    private final mockedSuggestionTournamentList() {
        def tournamentSuggestion1 = new TournamentSuggestion()
        tournamentSuggestion1.id = 8L
        def tournamentSuggestion2 = new TournamentSuggestion()
        tournamentSuggestion2.id = 9L

        [tournamentSuggestion1, tournamentSuggestion2]
    }

    private final mockedPlayer() {
        def player = new Player()
        player.id = 1L
        player.suggestedTournaments = mockedSuggestionTournamentList()
        player.localTournaments = mockedLocalTournamentList()
        player.favouriteTournaments = [mockedGlobalTournament(), mockedLocalTournament()]
        player
    }

    def """should check if service method create(AbstractTournament abstractTournament) is invoking
 repository method save(AbstractTournament abstractTournament) on the same object exactly once"""() {
        given:
        def tournament = mockedGlobalTournament()
        when:
        tournamentService.create(tournament)

        then:
        1 * tournamentRepository.save(tournament)
    }

    def """should check if service method create(AbstractTournament abstractTournament) is throwing
 an exception when abstract tournament is null"""() {
        when:
        tournamentService.create(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Creating tournament failed. Abstract tournament is null."
    }

    def """should check if service method ifCanBeOwnedByPlayer(AbstractTournament abstractTournament)
 is correctly setting owner based on '#tournament'"""() {
        when:
        def canBeOwned = tournamentService.ifCanBeOwnedByPlayer(tournament)

        then:
        canBeOwned == result

        where:
        tournament                 | result
        new TournamentGlobal()     | false
        new TournamentLocal()      | true
        new TournamentSuggestion() | true
    }

    def """should check if service findById(Long tournamentId) is returning
  abstract tournament entity object based on tournamentId"""() {
        given:
        tournamentRepository.findById(_ as Long) >> Optional.of(mockedLocalTournament())
        userService.getLoggedUser() >> mockedPlayer()
        userService.hasAuthority(ROLE_ADMIN) >> true

        when:
        def tournament = tournamentService.findById(TOURNAMENT_ID)

        then:
        tournament.id != null
    }

    def """should check if service method findById(Long tournamentId) is throwing
 an exception when tournament not belongs to logged user"""() {
        given:
        tournamentRepository.findById(_ as Long) >> Optional.of(mockedGlobalTournament())
        def tournamentNotBelongsToUserId = 1L

        and:
        userService.getLoggedUser() >> mockedPlayer()
        userService.hasAuthority(ROLE_USER) >> true

        when:
        tournamentService.findById(tournamentNotBelongsToUserId)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for tournament failed. No permission to processing with this tournament."
    }

    def """should check if service method delete(Long tournamentId) is invoking
 repository method delete() on the object with the same id exactly once"""() {
        given:
        tournamentRepository.findById(_ as Long) >> Optional.of(mockedGlobalTournament())

        and:
        userService.getLoggedUser() >> mockedPlayer()
        userService.hasAuthority(ROLE_ADMIN) >> true

        when:
        tournamentService.delete(TOURNAMENT_ID)

        then:
        1 * tournamentRepository.delete(mockedGlobalTournament())

    }

    def """should check if service method delete(Long tournamentId) is throwing
 an exception when tournamentId is null"""() {
        when:
        tournamentService.delete(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Deleting tournament failed. Tournament id is null."
    }

    def """should check if service findById(Long tournamentId) is throwing
 an exception when tournamentId is null"""() {
        when:
        tournamentService.findById(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for tournament failed.Tournament id is null."
    }

    def """should check if service method getTournamentListByTournamentScope
(TournamentScope tournamentScope) is returning list with valid type based on '#tournamentScope'"""() {
        given:
        userService.getLoggedPlayer() >> mockedPlayer()
        tournamentRepository.findGlobalTournaments() >> mockedGlobalTournamentList()

        when:
        def tournamentList = tournamentService.getTournamentListByTournamentScope(tournamentScope)

        then:
        tournamentList.every { clazz.isInstance(it) }

        where:
        tournamentScope | clazz
        LOCAL           | TournamentLocal.class
        SUGGESTION      | TournamentSuggestion.class
        GLOBAL          | TournamentGlobal.class

    }


    def """should check if service method ifUserHasAuthorityToProcessWithTournament
(AbstractTournament tournament, User user) will throw an exception when tournament is null"""() {
        when:
        tournamentService.ifUserHasAuthorityToProcessWithTournament(null, mockedPlayer())

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if user has authority to process with tournament failed. Tournament is null."
    }

    def """should check if service method ifUserHasAuthorityToProcessWithTournament
(AbstractTournament tournament, User user) will throw an exception when user is null"""() {
        when:
        tournamentService.ifUserHasAuthorityToProcessWithTournament(mockedLocalTournament(), null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Checking if user has authority to process with tournament failed. User is null."
    }

    def """should check if service findTournamentsPossibleToFavourites(Long playerId) is correctly
 returning empty list when there is no tournament possible to be in favourites"""() {

        given: "testPlayer has all tournaments from mockedGlobalList in favourites"
        def testPlayer = new Player()
        testPlayer.favouriteTournaments = mockedGlobalTournamentList()

        and: "test will check if method is correctly checking possibility of adding tournaments to favourites"
        tournamentRepository.findGlobalTournaments() >> mockedGlobalTournamentList()
        userService.findById(_ as Long) >> testPlayer

        when:
        def tournaments = tournamentService.findTournamentsPossibleToFavourites(PLAYER_ID)

        then: "all tournaments is already in favourite so list of possible tournaments is empty"
        tournaments.size() == 0
    }

    def """should check if service findTournamentsPossibleToFavourites(Long playerId) is correctly
 returning list of tournaments possible to be in favourites without duplicates"""() {

        given: "testPlayer has one tournament from mockedGlobalTournamentList in favourites"
        def testPlayer = new Player()
        testPlayer.favouriteTournaments = [mockedGlobalTournamentList()[0]]

        and: "mockedGlobalTournament list has 2 different global tournaments"
        tournamentRepository.findGlobalTournaments() >> mockedGlobalTournamentList()

        userService.findById(_ as Long) >> testPlayer

        when: "method is removing duplicates that's why size of tournaments is 1"
        def tournaments = tournamentService.findTournamentsPossibleToFavourites(PLAYER_ID)

        then:
        tournaments.size() == 1
    }

    def """should check if service findTournamentsPossibleToFavourites(Long playerId) is throwing
 an exception when player id is null """() {
        when:
        tournamentService.findTournamentsPossibleToFavourites(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for tournaments possible to be in user favourites failed. Player id is null."
    }

    def """should check if service method deleteFromFavourites(Long Player, Long tournamentId)
 is invoking repository method deleteTournamentFromFavourites(playerId, tournamentId) with same parameters exactly once"""() {
        when:
        tournamentService.deleteFromFavourites(PLAYER_ID, TOURNAMENT_ID)

        then:
        1 * tournamentRepository.deleteTournamentFromFavourites(PLAYER_ID, TOURNAMENT_ID)
    }

    def """should check if service method deleteFromFavourites(Long Player, Long tournamentId)
 is throwing an exception when playerId is null """() {
        when:
        tournamentService.deleteFromFavourites(null, TOURNAMENT_ID)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Deleting tournament from favourites failed. Player id is null."
    }

    def """should check if service method deleteFromFavourites(Long Player, Long tournamentId)
 is throwing an exception when tournamentId is null """() {
        when:
        tournamentService.deleteFromFavourites(PLAYER_ID, null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Deleting tournament from favourites failed. Tournament id is null."
    }

    def """should check if service method addToFavourites(Long Player, Long tournamentId)
 is invoking repository method addToFavourites(playerId, tournamentId) with same parameters exactly once"""() {
        when:
        tournamentService.addToFavourites(PLAYER_ID, TOURNAMENT_ID)

        then:
        1 * tournamentRepository.addTournamentToFavourites(PLAYER_ID, TOURNAMENT_ID)
    }

    def """should check if service method addToFavourites(Long Player, Long tournamentId)
 is throwing an exception when playerId is null """() {
        when:
        tournamentService.addToFavourites(null, TOURNAMENT_ID)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Adding tournament to favourites failed. Player id is null."
    }

    def """should check if service method addToFavourites(Long Player, Long tournamentId)
 is throwing an exception when tournamentId is null """() {
        when:
        tournamentService.addToFavourites(PLAYER_ID, null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Adding tournament to favourites failed. Tournament id is null."
    }

    def """should check if service method """() {
    }


}
