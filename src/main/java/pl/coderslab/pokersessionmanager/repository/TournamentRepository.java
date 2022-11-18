package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
            "where user_id = (:id)", nativeQuery = true)
    List<Tournament> findFavouriteTournaments(@Param("id") Long id);
}
