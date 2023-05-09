package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;
import pl.coderslab.pokersessionmanager.enums.TournamentSpeed;
import pl.coderslab.pokersessionmanager.enums.TournamentType;
import pl.coderslab.pokersessionmanager.repository.TournamentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.coderslab.pokersessionmanager.enums.RoleName.ROLE_ADMIN;
import static pl.coderslab.pokersessionmanager.enums.RoleName.ROLE_USER;
import static pl.coderslab.pokersessionmanager.enums.TournamentScope.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentService {

    //    private final TournamentMapper tournamentMapper;
    private final TournamentRepository tournamentRepository;
    //    private final UtilityService utilityService;
    private final UserService userService;

    private final PlayerService playerService;

    public void create(AbstractTournament abstractTournament) {
        if (abstractTournament == null) {
            throw new RuntimeException("Creating tournament failed. Abstract tournament is null.");
        }
        setOwnerIfPossible(abstractTournament);

        tournamentRepository.save(abstractTournament);
    }

    public boolean ifCanBeOwnedByPlayer(AbstractTournament abstractTournament) {
        return abstractTournament.getTournamentScope() == LOCAL || abstractTournament.getTournamentScope() == SUGGESTION;
    }

    public AbstractTournament findById(Long tournamentId) {
        if (tournamentId == null) {
            throw new RuntimeException("Searching for tournament failed. Tournament id is null.");
        }
        AbstractTournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert tournament by tournament Id: " + tournamentId));

        if (!ifUserHasAuthorityToProcessWithTournament(tournament, userService.getLoggedUser())) {
            throw new RuntimeException("Searching for tournament failed. No permission to processing with this tournament.");
        }

        return tournament;
    }

    public void delete(Long tournamentId) {
        if (tournamentId == null) {
            throw new RuntimeException("Deleting tournament failed. Tournament id is null.");
        }
        AbstractTournament tournament = findById(tournamentId);
//        removeTournamentFromSessionsBeforeDeleting(tournament);

        tournamentRepository.delete(tournament);
    }

//    public <T extends AbstractTournament> void removeTournamentFromSessionsBeforeDeleting(T tournamentToCheck) {
//
//        // removing as Administrator
//        if (userService.getLoggedUserAuthority().contains(Factory.create(ROLE_ADMIN))) {
//            Player tournamentOwner = null;
//            if (tournamentToCheck instanceof TournamentLocal) {
//                tournamentOwner = findTournamentOwner((TournamentLocal) tournamentToCheck);
//            } else if (tournamentToCheck instanceof TournamentSuggestion) {
//                tournamentOwner = findTournamentOwner((TournamentSuggestion) tournamentToCheck);
//            }
//            // Deleting Global tournament (without owner) as Administrator even is in sessions
//            if (tournamentOwner == null) {
//                for (Player player : playerService.findAllPlayers()) {
//                    player.getSessions()
//                            .stream()
//                            .filter(session -> session.getSessionTournaments().contains(tournamentToCheck))
//                            .forEach(session -> session.getSessionTournaments().remove(tournamentToCheck));
//                }
//
//            } else {
//// Deleting Local and Suggestion tournaments from user Session as administrator
//                for (Session session : tournamentOwner.getSessions()) {
//                    if (session.getSessionTournaments()
//                            .stream()
//                            .anyMatch(tournament -> tournament.getId().equals(tournamentToCheck.getId()))) {
//                        session.getSessionTournaments().remove(tournamentToCheck);
//
//                    }
//                }
//
//            }
//        } else {
//            // deleting as logged Player
//            Player loggedUser = (Player) userService.getLoggedUser();
//            List<Session> sessions = loggedUser.getSessions();
//            for (Session session : sessions) {
//                if (session.getSessionTournaments()
//                        .stream()
//                        .anyMatch(tournament -> tournament.getId().equals(tournamentToCheck.getId()))) {
//
//                    session.getSessionTournaments().remove(tournamentToCheck);
//                }
//            }
//        }
//    }
    //do sprawdzenia
    // usuwa z bazy z sesji turniej

    public List<TournamentLocal> findLocalTournamentsById(Long playerId) {
        if (playerId == null) {
            throw new RuntimeException("Searching for player's local tournaments failed. Player id is null.");
        }
        return tournamentRepository.findLocalTournamentsById(playerId);
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

//    public List<TournamentSuggestion> findSuggestedTournamentsById(Long userId) {
//        return tournamentRepository.findSuggestedTournamentsById(userId);
//    }

//    public List<AbstractTournament> findAllUserTournamentsById(Long userId) {
//        List<AbstractTournament> allUserTournamentsById = new ArrayList<>();
////        allUserTournamentsById.addAll(findGlobalTournamentsById(userId));
//        allUserTournamentsById.addAll(findSuggestedTournamentsById(userId));
//        allUserTournamentsById.addAll(findLocalTournamentsById(userId));
//
//        return allUserTournamentsById;
//    }

    private List<AbstractTournament> findAllPlayerTournaments(Player player) {
        if (player == null) {
            throw new RuntimeException("Searching for all player tournaments failed. Player is null.");
        }
        List<AbstractTournament> allUserTournaments = new ArrayList<>();
        allUserTournaments.addAll(player.getLocalTournaments());
        allUserTournaments.addAll(player.getSuggestedTournaments());

        return allUserTournaments;
    }

    public List<TournamentGlobal> findGlobalTournaments() {
        return tournamentRepository.findGlobalTournaments();
    }

    public List<AbstractTournament> findFavouriteTournaments(Long playerId) {
        if (playerId == null) {
            throw new RuntimeException("Searching for player's favourite tournaments failed. Player id is null.");
        }
        List<AbstractTournament> favouriteTournaments = tournamentRepository.findFavouriteTournaments(playerId);
        sortFavouriteTournamentsByScopeThenById(favouriteTournaments);
        return favouriteTournaments;
    }

    private void sortFavouriteTournamentsByScopeThenById(List<AbstractTournament> favouriteTournaments) {
        if (favouriteTournaments == null) {
            throw new RuntimeException("Sorting tournaments failed. List of favourite tournaments is null.");
        }
        favouriteTournaments
                .sort(Comparator
                        .comparing(AbstractTournament::getTournamentScope)
                        .reversed()
                        .thenComparing(AbstractTournament::getId));
    }

    public List<AbstractTournament> findTournamentsPossibleToFavourites(Long playerId) {
        if (playerId == null) {
            throw new RuntimeException("Searching for tournaments possible to be in user favourites failed. Player id is null.");
        }
        Player player = (Player) userService.findById(playerId);
        List<AbstractTournament> tournaments = new ArrayList<>();

        tournaments.addAll(player.getLocalTournaments());
        tournaments.addAll(findGlobalTournaments());

        tournaments.removeAll(player.getFavouriteTournaments());
        return tournaments;
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

    public void deleteFromFavourites(Long playerId, Long tournamentId) {
        if (playerId == null) {
            throw new RuntimeException("Deleting tournament from favourites failed. Player id is null.");
        }
        if (tournamentId == null) {
            throw new RuntimeException("Deleting tournament from favourites failed. Tournament id is null.");
        }
        tournamentRepository.deleteTournamentFromFavourites(playerId, tournamentId);
    }

    public void addToFavourites(Long playerId, Long tournamentId) {
        if (playerId == null) {
            throw new RuntimeException("Adding tournament to favourites failed. Player id is null.");
        }
        if (tournamentId == null) {
            throw new RuntimeException("Adding tournament to favourites failed. Tournament id is null.");
        }
        tournamentRepository.addTournamentToFavourites(playerId, tournamentId);
    }

    public List<AbstractTournament> getAvailableTournamentsForSessionOrderedByFavourites(Long playerId) {
        List<AbstractTournament> availableTournamentForSession = new ArrayList<>();
        availableTournamentForSession.addAll(findFavouriteTournaments(playerId));
        availableTournamentForSession.addAll(findTournamentsPossibleToFavourites(playerId));
        return availableTournamentForSession;
    }

    public List<? extends AbstractTournament> getTournamentListByTournamentScope(TournamentScope tournamentScope) {
//        Player loggedPlayer = userService.getLoggedPlayer();
        if (tournamentScope == null) {
            throw new RuntimeException("Searching for tournaments failed. Tournament scope is null.");
        }
        if (tournamentScope.equals(GLOBAL)) {
            return findGlobalTournaments();
        }

        Player loggedPlayer = userService.getLoggedPlayer();
        switch (tournamentScope) {
            case LOCAL -> {
                return loggedPlayer.getLocalTournaments();
            }
            case SUGGESTION -> {
                return loggedPlayer.getSuggestedTournaments();
            }
            default ->
                    throw new RuntimeException("Getting tournament list by scope failed. Unrecognized tournament scope: " + tournamentScope);
        }
    }

    public boolean ifUserHasAuthorityToProcessWithTournament(AbstractTournament tournament, User user) {
        if (tournament == null) {
            throw new RuntimeException("Checking if user has authority to process with tournament failed. Tournament is null.");
        }
        if (user == null) {
            throw new RuntimeException("Checking if user has authority to process with tournament failed. User is null.");
        }

        if (userService.hasAuthority(ROLE_ADMIN)) {
            return true;
        } else if (userService.hasAuthority(ROLE_USER)) {
            return findAllPlayerTournaments((Player) user)
                    .contains(tournament);
        }

        throw new RuntimeException("Checking if user has authority to process with tournament failed. Unrecognized authority: " + userService.getLoggedUserAuthority());
    }

//    public Player findTournamentOwner(TournamentLocal tournament) {
//        return playerService.findAllPlayers()
//                .stream()
//                .filter(player -> player.getLocalTournaments().contains(tournament))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public Player findTournamentOwner(TournamentSuggestion tournament) {
//        return playerService
//                .findAllPlayers()
//                .stream()
//                .filter(player -> player.getSuggestedTournaments().contains(tournament))
//                .findFirst()
//                .orElse(null);
//    }

    private void setOwnerIfPossible(AbstractTournament abstractTournament) {
        if (ifCanBeOwnedByPlayer(abstractTournament)) {
            abstractTournament.setPlayer(userService.getLoggedPlayer());
        }
    }

}