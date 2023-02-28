package pl.coderslab.pokersessionmanager.controller.tournament;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/tournament/{tournamentScopeFromUrl}")
public class TournamentController {
    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;


    // New Tournament added on the bottom in getTournament() method as @ModelAttribute, Path variable consumed there.
    @GetMapping("/add")
    public String addTournament() {
        return "tournament/tournamentForm";
    }

    @PostMapping("/add")
    public String addTournament(@Valid @ModelAttribute(name = "tournament") AbstractTournament tournament,
                                BindingResult result,
                                @PathVariable(value = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {
        if (result.hasErrors()) {
            return "tournament/tournamentForm";
        }
        tournamentService.create(tournament);

        return "redirect:/app/tournament/".concat(tournamentScopeFromUrl).concat("/all");
    }

    @GetMapping("/all")
    public String getTournaments(Model model,
                                 @PathVariable(value = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {
        model.addAttribute("tournamentList",
                tournamentService.getTournamentListByTournamentGenus(tournamentScopeFromUrl));

        return "tournament/userTournamentList";
    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournament(@PathVariable Long tournamentId,
                                   @PathVariable(value = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {
        tournamentService.delete(tournamentId);

        return "redirect:/app/tournament/".concat(tournamentScopeFromUrl).concat("/all");
    }

    @ModelAttribute("tournament")
    public AbstractTournament getTournament(@PathVariable(name = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {

        return Factory.create(tournamentService.convertStringToTournamentGenus(tournamentScopeFromUrl));
    }

    @ModelAttribute("tournamentGenus")
    public TournamentGenus getTournamentGenusToCorrectDisplayUrlOnTournamentLists(@PathVariable(name = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {

        return tournamentService.convertStringToTournamentGenus(tournamentScopeFromUrl);
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
        return pokerRoomService.findAllByRole();
    }
}

