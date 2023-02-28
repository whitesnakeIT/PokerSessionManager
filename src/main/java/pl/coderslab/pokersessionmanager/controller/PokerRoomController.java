package pl.coderslab.pokersessionmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.utilities.Factory;

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/poker_room")
public class PokerRoomController {

    private final PokerRoomService pokerRoomService;

    @GetMapping("all")
    public String showAllPokerRooms(Model model) {
        model.addAttribute("allPokerRooms", pokerRoomService.findAllByRole());

        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addPokerRoomGet(Model model) {
        model.addAttribute("pokerRoom", Factory.createPokerRoom());

        return "poker_room/pokerRoomForm";
    }

    @PostMapping({"/add","/edit/{id}"})
    public String addPokerRoomPost(@Valid PokerRoom pokerRoom,
                                   BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.create(pokerRoom);

        return "redirect:/poker_room/all";
    }

    @GetMapping("/edit/{id}")
    public String editPokerRoomGet(@PathVariable Long id,
                                   Model model) {
        model.addAttribute("pokerRoom", pokerRoomService.findById(id));

        return "poker_room/pokerRoomForm";
    }

//    @PostMapping("/edit/{id}")
//    public String editPokerRoomPost(@Valid PokerRoom pokerRoom,
//                                    BindingResult result) {
//        if (result.hasErrors()) {
//
//            return "poker_room/pokerRoomForm";
//        }
//        pokerRoomService.create(pokerRoom);
//
//        return "redirect:/poker_room/all";
//    }

    @GetMapping("/delete/{id}")
    public String deletePokerRoom(@PathVariable Long id) {
        pokerRoomService.delete(id);

        return "redirect:/poker_room/all";
    }
}
