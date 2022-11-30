package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;


    @GetMapping("/add")
    public String addNewTournamentGet(Model model) {
        model.addAttribute("tournament", new TournamentGlobal());

        return "tournament/form";
    }

    @PostMapping("/add")
    public String addNewTournamentGet(@Valid @ModelAttribute(name = "tournament") TournamentGlobal tournament, BindingResult result) {
        if (result.hasErrors()) {
            return "tournament/form";
        }
        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    @GetMapping("/all")
    public String getAllTournaments(Model model) {
        List<TournamentGlobal> tournamentList = tournamentService.findAll();
        model.addAttribute(tournamentList);
        return "tournament/globalTournamentList";
    }
//    @GetMapping("/global")
//    public String getAllTournamentsNotLoggedUser(Model model){
//
//    }

    @GetMapping("/edit/{id}")
    public String editTournamentGet(@PathVariable Long id, Model model) {
        TournamentGlobal tournament = tournamentService.findById(id);
        model.addAttribute("tournament", tournament);
        return "tournament/form";
    }

    @PostMapping("/edit/{id}")
    public String editTournamentPost(@Valid TournamentGlobal tournament,
                                     BindingResult result){
        if (result.hasErrors()) {
            return "tournament/form";
        }
        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    @GetMapping("/del/{tournamentId}")
    public String deleteTournament(@PathVariable Long tournamentId){
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
}

