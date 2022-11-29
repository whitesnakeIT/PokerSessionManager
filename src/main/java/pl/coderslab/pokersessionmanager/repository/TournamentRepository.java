package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface TournamentRepository extends JpaRepository<TournamentGlobal, Long> {

    @Query(value = "select t.* from user_favourite_tournaments uft " +
            "join tournaments t on t.id = uft.tournament_id " +
            "where user_id = (:userId)",
            nativeQuery = true)
    List<TournamentGlobal> findFavouriteTournaments(@Param("userId") Long userId);

    @Query(value = "select t.* " +
            "from tournaments t " +
            "left outer join user_favourite_tournaments uft " +
            "on t.id = uft.tournament_id " +
            "where tournament_id is null;",
            nativeQuery = true)
    List<TournamentGlobal> findNonFavouriteTournaments();

    @Query(value =
            "select distinct t.*" +
                    "from user_favourite_tournaments uft " +
                    "join tournaments t on t.id = uft.tournament_id " +
                    "where tournament_id not in " +
                    "(select tournament_id from user_favourite_tournaments where user_id=(:userId));",
            nativeQuery = true)
    List<TournamentGlobal> findFavouriteTournamentsNotBelongToUser(@Param("userId") Long userId);

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

    @Modifying
    @Query(value = "insert into user_suggestions_tournaments " +
            "(buy_in, handed, name, re_buy, speed, tournament_start_date_time, type, user_id)" +
            " values ((:buyIn),(:handed),(:name),(:reBuy),(:speed),(:tournamentStartDateTime),(:type),(:userId))",
            nativeQuery = true)
    void addTournamentToSuggestions(@Param("buyIn") double buyIn,
                                    @Param("handed") int handed,
                                    @Param("name") String name,
                                    @Param("reBuy") boolean reBuy,
                                    @Param("speed") String speed,
                                    @Param("tournamentStartDateTime") LocalDateTime tournamentStartDateTime,
                                    @Param("type") String type,
                                    @Param("userId") Long userId
    );

    @Query(value = "select * from user_suggestions_tournaments where user_id = (:userId)", nativeQuery = true)
    List<TournamentGlobal> findSuggestTournaments(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from user_suggestions_tournaments where  user_id = (:userId) and  id = (:tournamentId) ", nativeQuery = true)
    void deleteTournamentFromSuggestion(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);
}
