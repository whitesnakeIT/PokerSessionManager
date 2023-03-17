package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class TournamentRepositoryTest extends Specification {

    @Autowired
    private TournamentRepository tournamentRepository

    private static final PLAYER_ID = 1
    private static final TOURNAMENT_ID = 3

    def "should check if size of user favourite tournaments is correct"() {
        when:
        def favouriteTournaments = tournamentRepository.findFavouriteTournaments(PLAYER_ID)

        then:
        favouriteTournaments.size() == 2
    }

    def "should return correct size of favourite tournament list after adding one tournament to favourites "() {
        when:
        tournamentRepository.addTournamentToFavourites(PLAYER_ID, TOURNAMENT_ID)

        then:
        def favouriteTournaments = tournamentRepository.findFavouriteTournaments(PLAYER_ID)
        favouriteTournaments.size() == 3

    }

    def "should return correct size of favourite tournament list after deleting one tournament from favourites"() {
        when:
        tournamentRepository.deleteTournamentFromFavourites(PLAYER_ID, TOURNAMENT_ID)

        then:
        def favouriteTournaments = tournamentRepository.findFavouriteTournaments(PLAYER_ID)
        favouriteTournaments.size() == 2

    }


    def "should check if size of user local tournaments list is correct"() {
        when:
        def localTournaments = tournamentRepository.findLocalTournamentsById(PLAYER_ID)

        then:
        localTournaments.size() == 1
    }

    def "should check if size of user suggested tournaments list is correct"() {
        when:
        def suggestedTournaments = tournamentRepository.findSuggestedTournamentsById(PLAYER_ID)

        then:
        suggestedTournaments.isEmpty()
    }


    def "should check if size of tournament global list is correct"() {
        when:
        def globalTournaments = tournamentRepository.findGlobalTournaments()

        then:
        globalTournaments.size() == 2

    }
}
