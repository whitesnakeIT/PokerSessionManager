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
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoomLocal;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/poker_room")
public class PokerRoomLocalController {


    private final PokerRoomService pokerRoomService;

    @GetMapping("/all")
    public String showAllLocalPokerRooms(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
//        List<PokerRoomLocal> allPokerRooms = new ArrayList<>();
//        for (PokerRoom pokerRoom : pokerRoomService.findPokerRoomsByUserId(user.getId())) {
//            allPokerRooms.add((PokerRoomLocal) pokerRoom);
//        }
        List<? extends PokerRoom> allPokerRooms = pokerRoomService.findPokerRoomsByUserId(user.getId());
// do serwisu konwersja
        model.addAttribute("allPokerRooms", allPokerRooms);
        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addLocalPokerRoomGet(Model model) {

        model.addAttribute("pokerRoom", new PokerRoomLocal());
        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/add")
    public String addLocalPokerRoomPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                        @Valid PokerRoomLocal pokerRoom,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return "poker_room/pokerRoomForm";
        }
        User user = loggedUser.getUser();
        pokerRoom.setUser(user);
        pokerRoomService.create(pokerRoom);
        return "redirect:/poker_room/all";

    }


    @GetMapping("/edit/{pokerRoomId}")
    public String editLocalPokerRoomGet(@PathVariable Long pokerRoomId, Model model) {
        PokerRoomLocal pokerRoom = (PokerRoomLocal) pokerRoomService.findById(pokerRoomId);
        model.addAttribute(pokerRoom);
        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/edit/{id}")
    public String editLocalPokerRoomPost(@Valid PokerRoomLocal pokerRoom, BindingResult result, @AuthenticationPrincipal CurrentUser loggedUser) {
        if (result.hasErrors()) {
            return "poker_room/pokerRoomForm";
        }
        User user = loggedUser.getUser();
        pokerRoom.setUser(user);
        pokerRoomService.create(pokerRoom);
        return "redirect:/poker_room/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocalPokerRoom(@PathVariable Long id) {
        pokerRoomService.delete(id);
        return "redirect:/poker_room/all";

    }

}
