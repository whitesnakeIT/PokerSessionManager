package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournaments/local")
public class TournamentLocalController {

    private final TournamentService tournamentService;

    @GetMapping("/add")
    public String addTournamentsToLocal(Model model) {
        model.addAttribute("tournament", new TournamentLocal());
        return "tournament/form";
    }

    @PostMapping("/add")
    public String addTournamentToLocal(@AuthenticationPrincipal CurrentUser loggedUser,
                                       @Valid @ModelAttribute(name = "tournament") TournamentLocal tournamentLocal,
                                       BindingResult result) {

        if (result.hasErrors()) {
            return "tournament/form";
        }
        User user = loggedUser.getUser();
//        tournamentService.addTournamentToLocal(tournamentLocal, user.getId());


        return "redirect:/app/tournaments/local/all";
    }

//    @GetMapping("/all")
//    public String getLocalTournaments(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
//        User user = loggedUser.getUser();
//        List<TournamentLocal> localTournamentList = tournamentService.findLocalTournaments(user.getId());
//        model.addAttribute("localTournamentList", localTournamentList);
//        return "user/tournament/localTournamentList";
//
//    }

//    @GetMapping("/delete/{tournamentId}")
//    public String deleteTournamentFromLocal(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentId) {
//        User user = loggedUser.getUser();
//
//        tournamentService.deleteTournamentFromLocal(user.getId(), tournamentId);
//        return "redirect:/app/tournaments/local/all";
//    }

    @ModelAttribute("availableTournamentTypes")
    public List<String> getAvailableTournamentTypes() {
        return tournamentService.getAvailableTournamentTypes();
    }

    @ModelAttribute("availableTournamentSpeed")
    public List<String> getAvailableTournamentSpeed() {
        return tournamentService.getAvailableTournamentSpeed();
    }
}
