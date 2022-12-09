package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournament/suggest")
public class TournamentSuggestionController {
    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;

    @GetMapping("/add")
    public String addTournamentsToSuggestions(Model model) {
        model.addAttribute("tournament", new TournamentSuggestion());
        return "tournament/tournamentForm";
    }

    @PostMapping("/add")
    public String addTournamentToSuggestions(@AuthenticationPrincipal CurrentUser loggedUser,
                                             @Valid @ModelAttribute(name = "tournament") TournamentSuggestion tournamentSuggestion,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return "tournament/tournamentForm";
        }
        Player player = (Player) loggedUser.getUser();
        tournamentSuggestion.setPlayer(player);
        tournamentService.create(tournamentSuggestion);


        return "redirect:/app/tournament/suggest/all";
    }

    @GetMapping("/all")
    public String getSuggestTournaments(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<TournamentSuggestion> suggestionTournamentList = tournamentService.findSuggestedTournamentsById(user.getId());
        model.addAttribute("tournamentList", suggestionTournamentList);
        model.addAttribute("tournamentGenus", TournamentGenus.SUGGESTION);
        return "user/tournament/userTournamentList";

    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromSuggestions(@PathVariable Long tournamentId) {
        tournamentService.delete(tournamentId);
        return "redirect:/app/tournament/suggest/all";
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
