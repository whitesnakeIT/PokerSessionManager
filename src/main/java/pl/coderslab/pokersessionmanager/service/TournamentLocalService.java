package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.TournamentMapper;
import pl.coderslab.pokersessionmanager.repository.TournamentLocalRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentLocalService {

    private final TournamentMapper tournamentMapper;
    private final TournamentLocalRepository tournamentLocalRepository;

    public TournamentLocal findById(Long tournamentLocalId) {
        TournamentLocal tournamentLocal =
                tournamentLocalRepository.findById(tournamentLocalId).orElseThrow(() -> new RuntimeException("I can't find/convert tournamentLocal by tournamentLocal id"));
        return tournamentLocal;
    }
}
