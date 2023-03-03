package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilityService {

    private final PlayerService playerService;

    public boolean checkIfAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ROLE_ANONYMOUS"));
    }

    public boolean hasRole2(RoleName roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains(roleName.name()));
    }
//
//    public String setUrlByScope(AbstractTournament tournament) {
//
//        if (tournament instanceof TournamentGlobal) {
//            return TournamentGlobal.TOURNAMENT_SCOPE;
//        } else if (tournament instanceof TournamentLocal) {
//            return TournamentLocal.TOURNAMENT_SCOPE;
//        } else if (tournament instanceof TournamentSuggestion) {
//            return TournamentSuggestion.TOURNAMENT_SCOPE;
//        } else
//            throw new RuntimeException("I don't know what Tournament scope is in: " + tournament.getClass().getSimpleName() + " I can't set the URL");
//    }


    public Player getLoggedPlayer() {
        Player loggedUser = (Player) ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();
        playerService.loadLazyDataToPlayer(loggedUser);


        return loggedUser;
    }

//    public <T extends AbstractTournament> T convertAbstractTournamentToChildTournament(AbstractTournament abstractTournament, String tournamentScopeFromUrl) {
//        switch (tournamentScopeFromUrl) {
//            case TournamentGlobal.TOURNAMENT_SCOPE, TournamentLocal.TOURNAMENT_SCOPE, TournamentSuggestion.TOURNAMENT_GENUS -> {
//                return (T) abstractTournament;
//            }
//            default -> throw new RuntimeException("I can't convert AbstractTournament to  " + tournamentScopeFromUrl);
//        }
//    }
    // dlaczegp ?

//  public <T extends AbstractTournament> T convertAbstractTournamentToChildTournament(AbstractTournament abstractTournament) {
//
//      if (abstractTournament instanceof TournamentGlobal) {
//          return (TournamentGlobal) abstractTournament;
//      } else  if (abstractTournament instanceof TournamentLocal) {
//          return (TournamentLocal) abstractTournament;}
//      else  if (abstractTournament instanceof TournamentSuggestion) {
//          return (TournamentSuggestion) abstractTournament;}
//      else {
//          throw new RuntimeException("I can't convert AbstractTournament to his child");
//        }
//    }

    public <T> T convertAbstractTournament(AbstractTournament abstractTournament) {
        return (T) abstractTournament;

    }


}

