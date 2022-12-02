package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
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


    public void create(AbstractTournament tournament) {
        tournamentRepository.save(tournament);
    }

    public AbstractTournament findById(Long tournamentId) {
        AbstractTournament tournament = tournamentRepository
                .findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id."));
        return tournament;
    }
//
//    public List<AbstractTournament> findAll() {
//        return tournamentRepository.findAll();
//    }

    public void delete(Long tournamentId) {
        AbstractTournament tournament = tournamentRepository
                .findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id."));
        tournamentRepository.delete(tournament);
    }


//    public List<TournamentSlimDto> convertTournamentToSlimDto(List<TournamentGlobal> tournaments) {
//        return tournamentMapper.tournamentToTournamentSlimDto(tournaments);
//    }
//
//    public TournamentSlimDto convertTournamentToSlimDto(TournamentGlobal tournament) {
//        return tournamentMapper.tournamentToTournamentSlimDto(tournament);
//    }

//    public List<TournamentSlimDto> convertTournamentLocalToSlimDto(List<TournamentLocal> tournaments) {
//        return tournamentMapper.tournamentLocalToTournamentSlimDto(tournaments);
//    }
//
//    public TournamentSlimDto convertTournamentLocalToSlimDto(TournamentLocal tournament) {
//        return tournamentMapper.tournamentLocalToTournamentSlimDto(tournament);
//    }

    public List<TournamentLocal> findLocalTournamentsById(Long userId) {
        return tournamentRepository.findLocalTournamentsById(userId);
    }

    public List<TournamentSuggestion> findSuggestedTournamentsById(Long userId) {
        return tournamentRepository.findSuggestedTournamentsById(userId);
    }

    public List<TournamentGlobal> findGlobalTournaments() {
        return tournamentRepository.findGlobalTournaments();
    }

    public List<AbstractTournament> findFavouriteTournaments(Long userId) {

        return tournamentRepository.findFavouriteTournaments(userId);

    }

    public List<AbstractTournament> findTournamentsPossibleToFavourites(Long userId) {
        List<AbstractTournament> tournaments = new ArrayList<>();
        List<Long> favouriteTournamentsId = findFavouriteTournaments(userId)
                .stream()
                .map(AbstractTournament::getId).toList();
        tournaments.addAll(findLocalTournamentsById(userId));
        tournaments.addAll(findGlobalTournaments());

        List<AbstractTournament> tournamentsPossibleToFavourites =
                tournaments
                        .stream()
                        .filter(tournament -> !favouriteTournamentsId.contains(tournament.getId()))
                        .toList();
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

    public List<AbstractTournament> getAvailableTournamentsForSessionOrderByFavourites(Long userId) {
        List<AbstractTournament> availableTournamentForSession = new ArrayList<>(findFavouriteTournaments(userId));
        availableTournamentForSession.addAll(findTournamentsPossibleToFavourites(userId));
        return availableTournamentForSession;
    }


}
