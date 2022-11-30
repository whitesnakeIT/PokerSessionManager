package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.TournamentMapper;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    public List<TournamentSlimDto> convertTournamentToSlimDto(List<TournamentGlobal> tournaments) {
        return tournamentMapper.tournamentListToTournamentSlimDto(tournaments);
    }

    public TournamentSlimDto convertTournamentToSlimDto(TournamentGlobal tournament) {
        return tournamentMapper.tournamentToTournamentSlimDto(tournament);
    }

    public List<TournamentGlobal> findFavouriteTournaments(Long userId) {

        List<TournamentGlobal> favouriteTournaments = tournamentRepository.findFavouriteTournaments(userId);

        List<TournamentSlimDto> favouriteTournamentsSlim = convertTournamentToSlimDto(favouriteTournaments);

        return tournamentRepository.findFavouriteTournaments(userId);

    }

    //turnieje które nie są ulubione przez żadnego gracza
    public List<TournamentGlobal> findNonFavouriteTournaments() {
        List<TournamentGlobal> nonFavouriteTournaments = tournamentRepository.findNonFavouriteTournaments();

        List<TournamentSlimDto> nonFavouriteTournamentsSlim = convertTournamentToSlimDto(nonFavouriteTournaments);

        return tournamentRepository.findNonFavouriteTournaments();
    }

    // turnieje które są w ulubionych ale u innych graczy niż zalogowany
    public List<TournamentGlobal> findFavouriteTournamentsNotBelongToUser(Long userId) {
        List<TournamentGlobal> favouriteTournamentsNotBelongToUser = tournamentRepository.findFavouriteTournamentsNotBelongToUser(userId);
        List<TournamentSlimDto> favouriteTournamentsNotBelongToUserSlim = convertTournamentToSlimDto(favouriteTournamentsNotBelongToUser);
        return tournamentRepository.findFavouriteTournamentsNotBelongToUser(userId);
    }

    public List<TournamentGlobal> getListOfTournamentsPossibleToBeFavourites(Long userId) {
        List<TournamentGlobal> tournamentsPossibleToFavourites = new ArrayList<>();
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
        tournamentRepository.deleteTournamentFromSuggestion(userId, tournamentId);
    }


    public List<TournamentGlobal> getAvailableTournamentsForSessionOrderByFavourites(Long userId) {
        List<TournamentGlobal> availableTournamentForSession = new ArrayList<>(findFavouriteTournaments(userId));
        availableTournamentForSession.addAll(getListOfTournamentsPossibleToBeFavourites(userId));
        return availableTournamentForSession;
    }


    public List<TournamentGlobal> findTournamentsInSession(Long sessionId) {
        return tournamentRepository.findTournamentsInSession(sessionId);
    }
}
