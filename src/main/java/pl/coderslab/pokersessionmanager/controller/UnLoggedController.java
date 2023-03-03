package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

@Controller
@RequiredArgsConstructor
public class UnLoggedController {

    private final PokerRoomService pokerRoomService;

    private final TournamentService tournamentService;

    @GetMapping("/poker_room/all")
    public String showGlobalPokerPokerRooms(Model model) {
        model.addAttribute("globalPokerRooms", pokerRoomService.findGlobalPokerRooms());

        return "unlogged/globalPokerRoomList";
    }

    @GetMapping("/tournament/all")
    public String showGlobalTournamentsRooms(Model model) {
        model.addAttribute("globalTournaments", tournamentService.findGlobalTournaments());

        return "unlogged/globalTournamentList";
    }

}


