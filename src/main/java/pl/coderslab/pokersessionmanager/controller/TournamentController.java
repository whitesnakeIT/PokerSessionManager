package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.Tournament;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;


    @GetMapping("/add")
    public String addNewTournamentGet(Model model) {
        model.addAttribute(new Tournament());

        return "tournament/form";
    }

    @PostMapping("/add")
    public String addNewTournamentGet(@Valid Tournament tournament, BindingResult result) {
        if (result.hasErrors()) {
            return "tournament/form";
        }
        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    @GetMapping("/all")
    public String getAllTournaments(Model model) {
        List<Tournament> tournamentList = tournamentService.findAll();
        model.addAttribute(tournamentList);
        return "tournament/list";
    }

    @GetMapping("/edit/{id}")
    public String editTournamentGet(@PathVariable Long id, Model model){
        Optional<Tournament> tournamentOptional = tournamentService.findById(id);
        tournamentOptional.ifPresent(model::addAttribute);
        return "tournament/form";
    }

    @PostMapping("/edit/{id}")
    public String editTournamentPost(@Valid Tournament tournament, BindingResult result){
        if (result.hasErrors()) {
            return "tournament/form";
        }
        tournamentService.create(tournament);
        return "redirect:/tournament/all";
    }

    @GetMapping("/del/{id}")
    public String deleteTournament(@PathVariable Long id){
        Optional<Tournament> tournamentOptional = tournamentService.findById(id);
        tournamentOptional.ifPresent(tournamentService::delete);
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

