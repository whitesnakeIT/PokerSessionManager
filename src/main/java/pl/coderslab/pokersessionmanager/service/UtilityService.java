package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

import java.util.List;

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

    public String setUrlByGenus(AbstractTournament tournament) {

        if (tournament instanceof TournamentGlobal) {
            return TournamentGlobal.TOURNAMENT_GENUS;
        } else if (tournament instanceof TournamentLocal) {
            return TournamentLocal.TOURNAMENT_GENUS;
        } else if (tournament instanceof TournamentSuggestion) {
            return TournamentSuggestion.TOURNAMENT_GENUS;
        } else
            throw new RuntimeException("I don't know what Tournament genus is in: " + tournament.getClass().getSimpleName() + " I can't set the URL");
    }




    public Player getLoggedPlayer() {
        Player loggedUser = (Player) ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();
        playerService.loadLazyDataToPlayer(loggedUser);


        return loggedUser;
    }

    public <T extends AbstractTournament> T convertAbstractTournamentToChildTournament(AbstractTournament abstractTournament, String tournamentScopeFromUrl) {
        switch (tournamentScopeFromUrl) {
            case TournamentGlobal.TOURNAMENT_GENUS, TournamentLocal.TOURNAMENT_GENUS, TournamentSuggestion.TOURNAMENT_GENUS -> {
                return (T) abstractTournament;
            }
            default -> throw new RuntimeException("I can't convert AbstractTournament to  " + tournamentScopeFromUrl);
        }
    }
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

    public <T> T convertAbstractTournament(AbstractTournament abstractTournament){
        return (T) abstractTournament;

    }


    }

