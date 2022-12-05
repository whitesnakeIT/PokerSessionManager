package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoom;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.TournamentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"availableTournamentTypes","availableTournamentSpeed","availablePokerRooms"})
public class HomeController {

    private final TournamentService tournamentService;

    private final PokerRoomService pokerRoomService;
@GetMapping("/")
//@ResponseBody
    public String showIndexAndLoadData(Model model){
//    model.addAttribute("availableTournamentTypes", tournamentService.getAvailableTournamentTypes());
//    model.addAttribute("availableTournamentSpeed", tournamentService.getAvailableTournamentSpeed());
//    model.addAttribute("availablePokerRooms", pokerRoomService.findAll());
    return "index";
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
//@GetMapping("/error")
//@ResponseBody
//    public String showError(){
//    return "error?";
//}
}
