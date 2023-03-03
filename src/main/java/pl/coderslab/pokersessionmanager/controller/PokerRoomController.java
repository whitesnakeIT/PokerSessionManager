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
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.RedirectService;
import pl.coderslab.pokersessionmanager.service.UserService;
import pl.coderslab.pokersessionmanager.utilities.Factory;

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/poker_room/{pokerRoomScope}")
public class PokerRoomController {

    private final PokerRoomService pokerRoomService;

    private final RedirectService redirectService;


    @GetMapping("/all")
    public String showAllPokerRooms(@PathVariable(name = "pokerRoomScope") PokerRoomScope pokerRoomScope, Model model) {
        model.addAttribute("pokerRoomList", pokerRoomService.findAllByScope(pokerRoomScope));
//        model.addAttribute("pokerRoomScope",pokerRoomScope);
        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addPokerRoomGet(Model model) {
        model.addAttribute("pokerRoom", Factory.create(PokerRoom.class));

        return "poker_room/pokerRoomForm";
    }

    @PostMapping({"/add", "/edit/{id}"})
    public String addPokerRoomPost(@Valid PokerRoom pokerRoom,
                                   BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.create(pokerRoom);

//        return "redirect:/app/poker_room/all";
        return redirectService.sendRedirectAfterEditingEntity(PokerRoom.class, pokerRoom.getPlayer());
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
        PokerRoom pokerRoom = pokerRoomService.findById(id);
        pokerRoomService.delete(id);

//        return "redirect:/app/poker_room/all";
        return redirectService.sendRedirectAfterEditingEntity(PokerRoom.class, pokerRoom.getPlayer());
    }
}
