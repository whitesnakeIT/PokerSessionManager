package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TournamentRepository extends JpaRepository<AbstractTournament, Long> {

    @Query(value = "select t.* from user_favourite_tournaments uft " +
            "join tournaments t on t.id = uft.tournament_id " +
            "where uft.user_id = (:userId) " +
            "order by field (t.tournament_genus, 'local','global'),t.id",
            nativeQuery = true)
    List<AbstractTournament> findFavouriteTournaments(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from user_favourite_tournaments " +
            "where user_id=(:userId) and tournament_id=(:tournamentId)",
            nativeQuery = true)
    void deleteTournamentFromFavourites(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);

    @Modifying
    @Query(value = "insert  into  user_favourite_tournaments " +
            "(user_id,tournament_id) values " +
            "((:userId),(:tournamentId))",
            nativeQuery = true)
    void addTournamentToFavourites(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);

    @Query(value = "select * from tournaments where tournament_genus = 'local' and user_id = (:userId)",
            nativeQuery = true)
    List<TournamentLocal> findLocalTournamentsById(@Param("userId") Long userId);

    @Query(value = "select * from tournaments where tournament_genus = 'suggestion' and user_id = (:userId)",
            nativeQuery = true)
    List<TournamentSuggestion> findSuggestedTournamentsById(@Param("userId") Long userId);


    @Query(value = "select * from tournaments where tournament_genus = 'global'",
            nativeQuery = true)
    List<TournamentGlobal> findGlobalTournaments();
}

