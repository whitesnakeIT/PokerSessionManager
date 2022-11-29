package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSuggestionDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.TournamentMapper;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentMapper tournamentMapper;
    private final TournamentRepository tournamentRepository;


    public void create(TournamentGlobal tournament) {
        tournamentRepository.save(tournament);
    }

    public TournamentGlobal findById(Long tournamentId) {
        TournamentGlobal tournament = tournamentRepository
                .findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id."));
        return tournament;
    }

    public List<TournamentGlobal> findAll() {
        return tournamentRepository.findAll();
    }

    public void delete(Long tournamentId) {
        TournamentGlobal tournament = tournamentRepository
                .findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id."));
        tournamentRepository.delete(tournament);
    }


    public List<TournamentSlimDto> convertTournamentToDto(List<TournamentGlobal> tournaments) {
        return tournamentMapper.tournamentListToTournamentSlimDto(tournaments);
    }

    public TournamentSlimDto convertTournamentToDto(TournamentGlobal tournament) {
        return tournamentMapper.tournamentToTournamentSlimDto(tournament);
    }

    public List<TournamentSlimDto> findFavouriteTournaments(Long userId) {

        List<TournamentGlobal> favouriteTournaments = tournamentRepository.findFavouriteTournaments(userId);

        List<TournamentSlimDto> favouriteTournamentsSlim = convertTournamentToDto(favouriteTournaments);

        return favouriteTournamentsSlim;

    }

    //turnieje które nie są ulubione przez żadnego gracza
    public List<TournamentSlimDto> findNonFavouriteTournaments() {
        List<TournamentGlobal> nonFavouriteTournaments = tournamentRepository.findNonFavouriteTournaments();

        List<TournamentSlimDto> nonFavouriteTournamentsSlim = convertTournamentToDto(nonFavouriteTournaments);

        return nonFavouriteTournamentsSlim;
    }

    // turnieje które są w ulubionych ale u innych graczy niż zalogowany
    public List<TournamentSlimDto> findFavouriteTournamentsNotBelongToUser(Long userId) {
        List<TournamentGlobal> favouriteTournamentsNotBelongToUser = tournamentRepository.findFavouriteTournamentsNotBelongToUser(userId);
        List<TournamentSlimDto> favouriteTournamentsNotBelongToUserSlim = convertTournamentToDto(favouriteTournamentsNotBelongToUser);
        return favouriteTournamentsNotBelongToUserSlim;
    }

    public List<TournamentSlimDto> getListOfTournamentsPossibleToBeFavourites(Long userId) {
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
        tournamentRepository.deleteTournamentFromFavourites(userId, tournamentId);
    }

    public void addTournamentToFavourites(Long userId, Long tournamentId) {
        tournamentRepository.addTournamentToFavourites(userId, tournamentId);
    }

    public void addTournamentToSuggestions(TournamentSuggestion tournamentSuggestion, Long userId) {
        tournamentRepository.addTournamentToSuggestions(tournamentSuggestion.getBuyIn(),
                tournamentSuggestion.getHanded(),
                tournamentSuggestion.getName(),
                tournamentSuggestion.isReBuy(),
                tournamentSuggestion.getSpeed(),
                tournamentSuggestion.getTournamentStartDateTime(),
                tournamentSuggestion.getType(),
                userId);
    }

    public List<TournamentSuggestion> findSuggestTournaments(Long userId) {

        List<TournamentGlobal> tournamentsToConvert = tournamentRepository.findSuggestTournaments(userId);
        List<TournamentSuggestion> tournamentSuggestion = tournamentsToConvert.stream().map
                (tournamentMapper::tournamentToTournamentSuggestion).toList();
        return tournamentSuggestion;
    }

    public void deleteTournamentFromSuggestion(Long userId, Long tournamentId) {
        tournamentRepository.deleteTournamentFromSuggestion(userId,tournamentId);
    }


//    public void addTournamentToSuggestions(TournamentSuggestion tournamentSuggestion){
//        tournamentRepository.addTournamentToSuggestions(tournamentSuggestion);
//    }
}
