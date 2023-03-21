package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion
import spock.lang.Specification

@DataJpaTest
class TournamentRepositoryTest extends Specification {

    @Autowired
    private TournamentRepository tournamentRepository

    private static final PLAYER_ID = 1

    def """should check if repository method findFavouriteTournaments(@Param("playerId") Long playerId)
is correctly returning list of player favourite tournaments"""() {
        when:
        def favouriteTournaments = tournamentRepository.findFavouriteTournaments(PLAYER_ID)

        then:
        favouriteTournaments.size() == 2
    }

    def """should check if repository method addTournamentToFavourites(@Param("playerId") Long playerId,
 @Param("tournamentId") Long tournamentId) is correctly adding tournament to list of player favourite tournaments"""() {
        given:
        def favouriteTournamentsBeforeAdding = tournamentRepository.findFavouriteTournaments(PLAYER_ID)

        and:
        def testTournament = tournamentRepository.findById(3L).orElse(null)

        when:
        tournamentRepository.addTournamentToFavourites(PLAYER_ID, testTournament.id)

        then:
        def favouriteTournamentsAfterAdding = tournamentRepository.findFavouriteTournaments(PLAYER_ID)
        favouriteTournamentsAfterAdding.size() == favouriteTournamentsBeforeAdding.size() + 1
    }

    def """should check if repository method deleteTournamentFromFavourites(@Param("playerId") Long playerId,
 @Param("tournamentId") Long tournamentId) is correctly deleting tournament from list of player favourite tournaments"""() {
        given:
        def favouriteTournamentsBeforeDeleting = tournamentRepository.findFavouriteTournaments(PLAYER_ID)
        def existingTournamentId = 1L

        when:
        tournamentRepository.deleteTournamentFromFavourites(PLAYER_ID, existingTournamentId)
        def favouriteTournamentsAfterDeleting = tournamentRepository.findFavouriteTournaments(PLAYER_ID)

        then:
        favouriteTournamentsAfterDeleting.size() == favouriteTournamentsBeforeDeleting.size() - 1
    }

    def """should check if repository method findLocalTournamentsById(@Param("playerId") Long playerId)
 is correctly returning list of type TournamentLocal based on player id"""() {
        when:
        def localTournaments = tournamentRepository.findLocalTournamentsById(PLAYER_ID)

        then:
        verifyAll {
            localTournaments.size() == 1
            localTournaments.every { it instanceof TournamentLocal }
        }
    }

    def """should check if repository method findSuggestedTournamentsById(@Param("playerId") Long playerId)
 is correctly returning list of type TournamentSuggestion based on player id"""() {
        when:
        def suggestedTournaments = tournamentRepository.findSuggestedTournamentsById(PLAYER_ID)

        then:
        verifyAll {
            suggestedTournaments.size() == 1
            suggestedTournaments.every { it instanceof TournamentSuggestion }
        }
    }

    def """should check if repository method findGlobalTournaments() is correctly
 returning list of type TournamentGlobal"""() {
        when:
        def globalTournaments = tournamentRepository.findGlobalTournaments()

        then:
        verifyAll {
            globalTournaments.size() == 2
            globalTournaments.every { it instanceof TournamentGlobal }
        }
    }
}