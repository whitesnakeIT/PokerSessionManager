package pl.coderslab.pokersessionmanager.controller.poker_room;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoom;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/poker_room")
public class PokerRoomGlobalController {

    private final PokerRoomService pokerRoomService;

    @GetMapping("all")
    public String showAllPokerRooms(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<PokerRoom> allPokerRooms = pokerRoomService.findAll();

        model.addAttribute("allPokerRooms", allPokerRooms);
        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addPokerRoomGet(Model model) {

        model.addAttribute("pokerRoom", new PokerRoom());
        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/add")
    public String addPokerRoomPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                   @Valid PokerRoom pokerRoom,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "poker_room/pokerRoomForm";
        }

        User user = loggedUser.getUser();
        pokerRoomService.create(pokerRoom);
        return "redirect:/poker_room/all";

    }


    @GetMapping("/edit/{pokerRoomId}")
    public String editPokerRoomGet(@PathVariable Long pokerRoomId, Model model) {
        PokerRoom pokerRoom = pokerRoomService.findById(pokerRoomId);
        model.addAttribute(pokerRoom);
        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/edit/{id}")
    public String editPokerRoomPost(@Valid PokerRoom pokerRoom, BindingResult result, @AuthenticationPrincipal CurrentUser loggedUser) {
        if (result.hasErrors()) {
            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.create(pokerRoom);
        return "redirect:/poker_room/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePokerRoom(@PathVariable Long id) {
        pokerRoomService.delete(id);
        return "redirect:/poker_room/all";

    }

}
