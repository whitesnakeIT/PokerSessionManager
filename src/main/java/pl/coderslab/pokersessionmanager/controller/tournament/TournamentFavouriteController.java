package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.TournamentLocalService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournaments/favourite")
public class TournamentFavouriteController {
    private final TournamentService tournamentService;
    private final TournamentLocalService tournamentLocalService;

    @GetMapping("/local/add/{tournamentLocalId}")
    public String addTournamentLocalToFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentLocalId) {
        TournamentLocal tournamentLocal = tournamentLocalService.findById(tournamentLocalId);

        User user = loggedUser.getUser();
//        tournamentService.addTournamentLocalToFavourites(user.getId(), tournamentLocal);
        return "redirect:/app/tournaments/favourite/all";
    }

    @GetMapping("/add/{tournamentPossibleToFavourites}")
    public String addTournamentToFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentPossibleToFavourites) {
        User user = loggedUser.getUser();
        tournamentService.addTournamentToFavourites(user.getId(), tournamentPossibleToFavourites);
        return "redirect:/app/tournaments/favourite/all";
    }


    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentId) {
        User user = loggedUser.getUser();
        tournamentService.deleteTournamentFromFavourites(user.getId(), tournamentId);

        return "redirect:/app/tournaments/favourite/all";
    }


    @GetMapping("/all")
    public String getFavouriteTournaments(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();

        List<TournamentSlimDto> favouriteTournaments =
                tournamentService.convertTournamentToSlimDto(
                        tournamentService.
                                findFavouriteTournaments(user.getId()));
        List<TournamentSlimDto> tournamentsPossibleToFavourites =
                tournamentService.convertTournamentToSlimDto(
                        tournamentService.getListOfTournamentsPossibleToBeFavourites(user.getId()));
        List<TournamentSlimDto> localTournaments =
                tournamentService.convertTournamentLocalToSlimDto(
                        (tournamentService.findLocalTournaments(user.getId())));
        model.addAttribute("favouriteTournaments", favouriteTournaments);
        model.addAttribute("tournamentsPossibleToFavourites", tournamentsPossibleToFavourites);
        model.addAttribute("localTournaments", localTournaments);
        return "user/tournament/favouriteTournamentList";
    }

    @ModelAttribute("availableTournamentTypes")
    public List<String> getAvailableTournamentTypes() {
        return tournamentService.getAvailableTournamentTypes();
    }

    @ModelAttribute("availableTournamentSpeed")
    public List<String> getAvailableTournamentSpeed() {
        return tournamentService.getAvailableTournamentSpeed();
    }
}
