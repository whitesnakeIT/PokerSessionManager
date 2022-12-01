package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;

public interface TournamentLocalRepository extends JpaRepository<TournamentLocal, Long> {
}
