package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournaments/suggest")
public class TournamentSuggestionController {
    private final TournamentService tournamentService;

    @GetMapping("/add")
    public String addTournamentsToSuggestions(Model model) {
        model.addAttribute("tournament", new TournamentSuggestion());
        return "tournament/form";
    }

    @PostMapping("/add")
    public String addTournamentToSuggestions(@AuthenticationPrincipal CurrentUser loggedUser,
                                             @Valid @ModelAttribute(name = "tournament") TournamentSuggestion tournamentSuggestion,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return "tournament/form";
        }
        User user = loggedUser.getUser();
        tournamentService.addTournamentToSuggestions(tournamentSuggestion, user.getId());


        return "redirect:/app/tournaments/suggest/all";
    }

    @GetMapping("/all")
    public String getSuggestTournaments(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<TournamentSuggestion> suggestionTournamentList = tournamentService.findSuggestTournaments(user.getId());
        model.addAttribute("suggestionTournamentList", suggestionTournamentList);
        return "user/tournament/suggestionTournamentList";

    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromSuggestions(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentId) {
        User user = loggedUser.getUser();

        tournamentService.deleteTournamentFromSuggestion(user.getId(), tournamentId);
        return "redirect:/app/tournaments/suggest/all";
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
