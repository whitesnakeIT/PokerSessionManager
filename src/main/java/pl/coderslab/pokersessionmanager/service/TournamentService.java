package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.TournamentMapper;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;
import pl.coderslab.pokersessionmanager.utilities.Factory;

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
    private final UtilityService utilityService;
    private final UserService userService;

    private final PlayerService playerService;

    // boiler ???
    public void create(AbstractTournament abstractTournament) {
        if (abstractTournament instanceof TournamentSuggestion) {
            TournamentSuggestion tournament = utilityService.convertAbstractTournament(abstractTournament);
            tournament.setPlayer((Player) userService.getLoggedUser());
            tournamentRepository.save(tournament);
        } else if (abstractTournament instanceof TournamentLocal) {
            TournamentLocal tournament = utilityService.convertAbstractTournament(abstractTournament);
            tournament.setPlayer((Player) userService.getLoggedUser());
            tournamentRepository.save(tournament);
        }

        tournamentRepository.save(abstractTournament);
    }

    public AbstractTournament findById(Long tournamentId) {
        AbstractTournament tournament = tournamentRepository
                .findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id: " + tournamentId));

        User loggedUser = userService.findById(userService.getLoggedUser().getId());

        if (!checkIfTournamentBelongsToUser(tournament, loggedUser)) {
            throw new RuntimeException("Tournament not belongs to user, tournament Id: " + tournamentId);
        }
        return tournament;

    }
//
//    public List<AbstractTournament> findAll() {
//        return tournamentRepository.findAll();
//    }

    public void delete(Long tournamentId) {
        AbstractTournament tournament = findById(tournamentId);
        removeTournamentFromSessionsBeforeDeleting(tournament);
        tournamentRepository.delete(tournament);

    }
    //do sprawdzenia
    // usuwa z bazy z sesji turniej

    public <T extends AbstractTournament> void removeTournamentFromSessionsBeforeDeleting(T tournamentToCheck) {

        // removing as Administrator
        if (userService.getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_ADMIN))) {
            Player tournamentOwner = null;
            if (tournamentToCheck instanceof TournamentLocal) {
                tournamentOwner = findTournamentOwner((TournamentLocal) tournamentToCheck);
            } else if (tournamentToCheck instanceof TournamentSuggestion) {
                tournamentOwner = findTournamentOwner((TournamentSuggestion) tournamentToCheck);
            }
            // Deleting Global tournament (without owner) as Administrator even is in sessions
            if (tournamentOwner == null) {
                for (Player player : playerService.findAllPlayers()) {
                    player.getSessions()
                            .stream()
                            .filter(session -> session.getSessionTournaments().contains(tournamentToCheck))
                            .forEach(session -> session.getSessionTournaments().remove(tournamentToCheck));
                }

            } else {
// Deleting Local and Suggestion tournaments from user Session as administrator
                for (Session session : tournamentOwner.getSessions()) {
                    if (session.getSessionTournaments()
                            .stream()
                            .anyMatch(tournament -> tournament.getId().equals(tournamentToCheck.getId()))) {
                        session.getSessionTournaments().remove(tournamentToCheck);

                    }
                }

            }
        } else {
            // deleting as logged Player
            Player loggedUser = (Player) userService.getLoggedUser();
            List<Session> sessions = loggedUser.getSessions();
            for (Session session : sessions) {
                if (session.getSessionTournaments()
                        .stream()
                        .anyMatch(tournament -> tournament.getId().equals(tournamentToCheck.getId()))) {

                    session.getSessionTournaments().remove(tournamentToCheck);
                }
            }
        }
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

//    public List<TournamentGlobal> findGlobalTournamentsById(Long userId) {
//        return tournamentRepository.findGlobalTournamentsById(userId);
//    }

    public List<AbstractTournament> findAllUserTournamentsById(Long userId) {
        List<AbstractTournament> allUserTournamentsById = new ArrayList<>();
//        allUserTournamentsById.addAll(findGlobalTournamentsById(userId));
        allUserTournamentsById.addAll(findSuggestedTournamentsById(userId));
        allUserTournamentsById.addAll(findLocalTournamentsById(userId));
        return allUserTournamentsById;

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

        return Arrays.stream(TournamentSpeed.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .map(item -> item.replaceAll("_", " "))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableTournamentTypes() {

        return Arrays.stream(TournamentType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .map(item -> item.replaceAll("_", " "))
                .collect(Collectors.toList());
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

    public List<? extends AbstractTournament> getTournamentListByTournamentScope(TournamentScope tournamentScope) {

//        TournamentScope tournamentScope = convertStringToTournamentScope(tournamentScope);
        switch (tournamentScope) {
            case GLOBAL -> {
                return findGlobalTournaments();
            }
            case LOCAL -> {
                return findLocalTournamentsById(userService.getLoggedUser().getId());
            }
            case SUGGESTION -> {
                return findSuggestedTournamentsById(userService.getLoggedUser().getId());
            }
            default -> throw new RuntimeException("I can't find list of tournaments by scope: " + tournamentScope);
        }
    }

//    public TournamentScope convertStringToTournamentScope(String tournamentScope) {
//
//        switch (tournamentScope) {
//            case TournamentGlobal.TOURNAMENT_SCOPE -> {
//                return TournamentScope.GLOBAL;
//            }
//            case TournamentLocal.TOURNAMENT_SCOPE -> {
//                return TournamentScope.LOCAL;
//            }
//            case TournamentSuggestion.TOURNAMENT_SCOPE -> {
//                return TournamentScope.SUGGESTION;
//            }
//            default -> throw new RuntimeException("I don't know Tournament cope under string: " + tournamentScope);
//        }
//
//    }

    public <T extends AbstractTournament> boolean checkIfTournamentBelongsToUser(T tournament, User user) {
        // Administrator can delete all tournaments
        if (user.hasRole(RoleName.ROLE_ADMIN)) {
            return true;
        }

        return findAllUserTournamentsById(user.getId())
                .stream()
                .anyMatch(abstractTournament -> abstractTournament.getId().equals(tournament.getId()));
    }

    public Player findTournamentOwner(TournamentLocal tournament) {
        return playerService.findAllPlayers()
                .stream()
                .filter(player -> player.getLocalTournaments().contains(tournament))
                .findFirst()
                .orElse(null);
    }

    public Player findTournamentOwner(TournamentSuggestion tournament) {
        return playerService
                .findAllPlayers()
                .stream()
                .filter(player -> player.getSuggestedTournaments().contains(tournament))
                .findFirst()
                .orElse(null);
    }
}