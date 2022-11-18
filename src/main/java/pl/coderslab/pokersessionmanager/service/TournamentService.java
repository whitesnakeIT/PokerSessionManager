package pl.coderslab.pokersessionmanager.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Tournament;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.MapStructMapperImpl;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
//@RequiredArgsConstructor
@Getter
public class TournamentService {

    private final List<String> availableTournamentTypes;
    private final List<String> availableTournamentSpeed;

    private final MapStructMapperImpl mapStructMapper;

    public TournamentService(List<String> availableTournamentTypes,
                             List<String> availableTournamentSpeed,
                             MapStructMapperImpl mapStructMapper,
                             TournamentRepository tournamentRepository) {
        this.mapStructMapper = mapStructMapper;
        this.tournamentRepository = tournamentRepository;
        this.availableTournamentTypes = List.of(
                "normal",
                "progressive-knockout",
                "knockout",
                "zoom"
        );
        this.availableTournamentSpeed = List.of(
                "hyper",
                "turbo",
                "regular",
                "slow",
                "zoom"
        );
    };

    private final TournamentRepository tournamentRepository;

    public void create(Tournament tournament){tournamentRepository.save(tournament);}

    public Optional<Tournament> findById(Long id){return tournamentRepository.findById(id);}

    public List<Tournament> findAll(){return tournamentRepository.findAll();}

    public void delete(Tournament tournament){tournamentRepository.delete(tournament);}

    public List <TournamentSlimDto> findFavouriteTournaments(Long id){

        List<Tournament> favouriteTournaments = tournamentRepository.findFavouriteTournaments(id);

        List<TournamentSlimDto> favouriteTournamentsSlim = favouriteTournaments
                .stream()
                .map(mapStructMapper::tournamentToTournamentSlimDto)
                .collect(Collectors.toList());
//        favouriteTournamentsSlim.forEach(System.out::println);

        return favouriteTournamentsSlim;

    }

//    public List<String> getAvailableTournamentTypes(){
//
//    }
}
