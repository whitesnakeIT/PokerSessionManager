package pl.coderslab.pokersessionmanager.controller.tournament;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;
import pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room.PokerRoomSlim;
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
                                 @PathVariable(value = "tournamentScopeFromUrl") TournamentScope tournamentScopeFromUrl) {
        model.addAttribute("tournamentList",
                tournamentService.getTournamentListByTournamentScope(tournamentScopeFromUrl));

        return "tournament/userTournamentList";
    }

    @GetMapping("/delete/{tournamentId}")
    public String deleteTournament(@PathVariable Long tournamentId,
                                   @PathVariable(value = "tournamentScopeFromUrl") String tournamentScopeFromUrl) {
        tournamentService.delete(tournamentId);

        return "redirect:/app/tournament/".concat(tournamentScopeFromUrl).concat("/all");
    }

    @ModelAttribute("tournament")
    public AbstractTournament getTournament(@PathVariable(name = "tournamentScopeFromUrl") TournamentScope tournamentScopeFromUrl) {

//        return Factory.create(tournamentService.convertStringToTournamentScope(tournamentScopeFromUrl));
        return Factory.create(tournamentScopeFromUrl);
    }

//    @ModelAttribute("tournamentScope")
//    public TournamentScope getTournamentScopeToCorrectDisplayUrlOnTournamentLists(@PathVariable(name = "tournamentScopeFromUrl") TournamentScope tournamentScopeFromUrl) {
//
//       return tournamentScopeFromUrl;
////        return tournamentService.convertStringToTournamentScope(tournamentScopeFromUrl);
//    }

    @ModelAttribute("availableTournamentTypes")
    public List<String> getAvailableTournamentTypes() {
        return tournamentService.getAvailableTournamentTypes();
    }

    @ModelAttribute("availableTournamentSpeed")
    public List<String> getAvailableTournamentSpeed() {
        return tournamentService.getAvailableTournamentSpeed();
    }

    @ModelAttribute("availablePokerRooms")
    public List<PokerRoomSlim> getAvailablePokerRoomsSlim() {
        return pokerRoomService.findAvailablePokerRoomsSlimForPlayer();
    }
}

