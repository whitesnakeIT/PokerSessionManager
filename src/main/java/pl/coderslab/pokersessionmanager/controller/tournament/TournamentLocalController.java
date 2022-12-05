package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournaments/local")
public class TournamentLocalController {

    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;

    @GetMapping("/add")
    public String addTournamentsToLocal(Model model) {
        model.addAttribute("tournament", new TournamentLocal());
        return "tournament/tournamentForm";
    }

    @PostMapping("/add")
    public String addTournamentToLocal(@AuthenticationPrincipal CurrentUser loggedUser,
                                       @Valid @ModelAttribute(name = "tournament") TournamentLocal tournamentLocal,
                                       BindingResult result) {

        if (result.hasErrors()) {
            return "tournament/tournamentForm";
        }
        User user = loggedUser.getUser();
        tournamentLocal.setUser(user);
        tournamentService.create(tournamentLocal);


        return "redirect:/app/tournaments/local/all";
    }

    @GetMapping("/all")
    public String getLocalTournaments(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<TournamentLocal> localTournamentList = tournamentService.findLocalTournamentsById(user.getId());
        model.addAttribute("localTournamentList", localTournamentList);
        return "user/tournament/localTournamentList";

    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournamentFromLocal(@PathVariable Long tournamentId) {
        tournamentService.delete(tournamentId);
        return "redirect:/app/tournaments/local/all";
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
