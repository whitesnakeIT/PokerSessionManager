package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;

//import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TournamentRepository extends JpaRepository<AbstractTournament, Long> {

    @Query(value = "select t.* from player_favourite_tournaments pft " +
            "join tournaments t on t.id = pft.tournament_id " +
            "where pft.player_id = (:playerId) " +
            "order by field (t.tournament_genus, 'local','global'),t.id",
            nativeQuery = true)
    List<AbstractTournament> findFavouriteTournaments(@Param("playerId") Long playerId);

    @Modifying
    @Query(value = "delete from player_favourite_tournaments " +
            "where player_id=(:playerId) and tournament_id=(:tournamentId)",
            nativeQuery = true)
    void deleteTournamentFromFavourites(@Param("playerId") Long playerId, @Param("tournamentId") Long tournamentId);

    @Modifying
    @Query(value = "insert  into  player_favourite_tournaments " +
            "(player_id,tournament_id) values " +
            "((:playerId),(:tournamentId))",
            nativeQuery = true)
    void addTournamentToFavourites(@Param("playerId") Long playerId, @Param("tournamentId") Long tournamentId);

    @Query(value = "select * from tournaments where tournament_genus = 'local' and player_id = (:playerId)",
            nativeQuery = true)
    List<TournamentLocal> findLocalTournamentsById(@Param("playerId") Long playerId);

    @Query(value = "select * from tournaments where tournament_genus = 'suggestion' and player_id = (:playerId)",
            nativeQuery = true)
    List<TournamentSuggestion> findSuggestedTournamentsById(@Param("playerId") Long playerId);


    @Query(value = "select * from tournaments where tournament_genus = 'global'",
            nativeQuery = true)
    List<TournamentGlobal> findGlobalTournaments();
}

