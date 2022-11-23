package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Tournament;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query(value = "select t.* from user_favourite_tournaments uft " +
            "join tournaments t on t.id = uft.tournament_id " +
            "where user_id = (:userId)",
            nativeQuery = true)
    List<Tournament> findFavouriteTournaments(@Param("userId") Long userId);
    @Query(value = "select t.* " +
            "from tournaments t " +
            "left outer join user_favourite_tournaments uft " +
            "on t.id = uft.tournament_id " +
            "where tournament_id is null;",
            nativeQuery = true)
    List<Tournament> findNonFavouriteTournaments();

    @Query(value =
            "select distinct t.*" +
            "from user_favourite_tournaments uft " +
            "join tournaments t on t.id = uft.tournament_id " +
            "where tournament_id not in " +
            "(select tournament_id from user_favourite_tournaments where user_id=(:userId));",
            nativeQuery = true)
    List<Tournament> findFavouriteTournamentsNotBelongToUser(@Param("userId") Long userId);

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


}
