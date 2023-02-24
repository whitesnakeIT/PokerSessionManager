package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournament/favourites")
public class TournamentFavouriteController {
    private final TournamentService tournamentService;

    private final UserService userService;

    @GetMapping("/add/{tournamentPossibleToFavourites}")
    public String addTournamentToFavourites(@PathVariable Long tournamentPossibleToFavourites) {
        User user = userService.getLoggedUser();
        tournamentService.addTournamentToFavourites(user.getId(), tournamentPossibleToFavourites);

        return "redirect:/app/tournament/favourites";
    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromFavourites(@PathVariable Long tournamentId) {
        User user = userService.getLoggedUser();
        tournamentService.deleteTournamentFromFavourites(user.getId(), tournamentId);

        return "redirect:/app/tournament/favourites";
    }

    @GetMapping("")
    public String showFavouriteTournamentsIndex(Model model) {
        User user = userService.getLoggedUser();
        List<AbstractTournament> tournamentsPossibleToFavourites =
                tournamentService.findTournamentsPossibleToFavourites(user.getId());
        List<AbstractTournament> favouriteTournaments =
                tournamentService.findFavouriteTournaments(user.getId());

        model.addAttribute("favouriteTournaments", favouriteTournaments);
        model.addAttribute("tournamentsPossibleToFavourites", tournamentsPossibleToFavourites);

        return "tournament/favouriteTournamentList";
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
