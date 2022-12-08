package pl.coderslab.pokersessionmanager.controller.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentGlobalController {

    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;


    @GetMapping("/add")
    public String addNewTournamentGet(Model model) {
        model.addAttribute("tournament", new TournamentGlobal());

        return "tournament/tournamentForm";
    }

    @PostMapping("/add")
    public String addNewTournamentGet(@Valid @ModelAttribute(name = "tournament") TournamentGlobal tournament,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "tournament/tournamentForm";
        }

        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    // maybe in admin controller ?
    @GetMapping("/all")
    public String getAllTournaments(Model model) {
        List<TournamentGlobal> tournamentList = tournamentService.findGlobalTournaments();
        model.addAttribute(tournamentList);
        return "tournament/globalTournamentList";
    }

//    @GetMapping("/global")
//    public String getAllTournamentsNotLoggedUser(Model model){
//        List<TournamentGlobal> globalTournaments = tournamentService.findGlobalTournaments();
//        return ""
//    }

    @GetMapping("/edit/{id}")
    public String editTournamentGet(@PathVariable Long id, Model model) {
        TournamentGlobal tournament = (TournamentGlobal) tournamentService.findById(id);
        model.addAttribute("tournament", tournament);
        return "tournament/tournamentForm";
    }

    @PostMapping("/edit/{id}")
    public String editTournamentPost(@Valid TournamentGlobal tournament,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "tournament/tournamentForm";
        }
        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    @GetMapping("/del/{tournamentId}")
    public String deleteTournament(@PathVariable Long tournamentId) {
        tournamentService.delete(tournamentId);
        return "redirect:/tournament/all";
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

