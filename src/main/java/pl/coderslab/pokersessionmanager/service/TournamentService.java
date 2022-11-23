package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Tournament;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.TournamentMapper;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentMapper tournamentMapper;
    private final TournamentRepository tournamentRepository;


    public void create(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public void delete(Tournament tournament) {
        tournamentRepository.delete(tournament);
    }

    public List<TournamentSlimDto> convertTournamentToDto(List <Tournament> tournaments){
        return tournamentMapper.tournamentListToTournamentSlimDto(tournaments);
    }
    public TournamentSlimDto convertTournamentToDto(Tournament tournament){
        return tournamentMapper.tournamentToTournamentSlimDto(tournament);
    }
    public List<TournamentSlimDto> findFavouriteTournaments(Long userId) {

        List<Tournament> favouriteTournaments = tournamentRepository.findFavouriteTournaments(userId);

        List<TournamentSlimDto> favouriteTournamentsSlim = convertTournamentToDto(favouriteTournaments);

        return favouriteTournamentsSlim;

    }

//turnieje które nie są ulubione przez żadnego gracza
    public List<TournamentSlimDto> findNonFavouriteTournaments() {
        List<Tournament> nonFavouriteTournaments = tournamentRepository.findNonFavouriteTournaments();

        List<TournamentSlimDto> nonFavouriteTournamentsSlim = convertTournamentToDto(nonFavouriteTournaments);

        return nonFavouriteTournamentsSlim;
    }

    // turnieje które są w ulubionych ale u innych graczy niż zalogowany
    public List<TournamentSlimDto> findFavouriteTournamentsNotBelongToUser(Long userId){
        List<Tournament> favouriteTournamentsNotBelongToUser = tournamentRepository.findFavouriteTournamentsNotBelongToUser(userId);
        List<TournamentSlimDto> favouriteTournamentsNotBelongToUserSlim = convertTournamentToDto(favouriteTournamentsNotBelongToUser);
        return favouriteTournamentsNotBelongToUserSlim;
    }

    public List<TournamentSlimDto> getListOfTournamentsPossibleToBeFavourites(Long userId){
       List<TournamentSlimDto> tournamentsPossibleToFavourites = new ArrayList<>();
        tournamentsPossibleToFavourites.addAll(findNonFavouriteTournaments());
        tournamentsPossibleToFavourites.addAll(findFavouriteTournamentsNotBelongToUser(userId));
        return tournamentsPossibleToFavourites;
    }

    public List<String> getAvailableTournamentSpeed() {

        List<String> availableSpeedList =
                Arrays.stream(TournamentSpeed.values())
                        .map(Enum::name)
                        .map(String::toLowerCase)
                        .map(item -> item.replaceAll("_", " "))
                        .collect(Collectors.toList());
        return availableSpeedList;
    }

    public List<String> getAvailableTournamentTypes() {

        List<String> availableTypes =
                Arrays.stream(TournamentType.values())
                        .map(Enum::name)
                        .map(String::toLowerCase)
                        .map(item -> item.replaceAll("_", " "))
                        .collect(Collectors.toList());
        return availableTypes;
    }

    public void deleteTournamentFromFavourites(Long userId, Long tournamentId) {
            tournamentRepository.deleteTournamentFromFavourites(userId,tournamentId);
    }

    public void addTournamentToFavourites(Long userId, Long tournamentId){
        tournamentRepository.addTournamentToFavourites(userId,tournamentId);
    }
}
