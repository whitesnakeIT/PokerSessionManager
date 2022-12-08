package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournament/favourites")
public class TournamentFavouriteController {
    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;

    @GetMapping("/add/{tournamentPossibleToFavourites}")
    public String addTournamentToFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser,
                                               @PathVariable Long tournamentPossibleToFavourites) {
        User user = loggedUser.getUser();
        tournamentService.addTournamentToFavourites(user.getId(), tournamentPossibleToFavourites);
        return "redirect:/app/tournament/favourites";
    }


    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser,
                                                    @PathVariable Long tournamentId) {
        User user = loggedUser.getUser();
        tournamentService.deleteTournamentFromFavourites(user.getId(), tournamentId);

        return "redirect:/app/tournament/favourites";
    }


    @GetMapping("")
    public String getFavouriteTournaments(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();

        List<AbstractTournament> tournamentsPossibleToFavourites = tournamentService.findTournamentsPossibleToFavourites(user.getId());
        List<AbstractTournament> favouriteTournaments = tournamentService.findFavouriteTournaments(user.getId());
        model.addAttribute("favouriteTournaments", favouriteTournaments);
        model.addAttribute("tournamentsPossibleToFavourites", tournamentsPossibleToFavourites);
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

    @ModelAttribute("availablePokerRooms")
    public List<PokerRoom> getAvailablePokerRooms() {
        return pokerRoomService.findAll();
    }
}
