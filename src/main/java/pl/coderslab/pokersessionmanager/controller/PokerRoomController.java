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
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room.PokerRoomSlim;
import pl.coderslab.pokersessionmanager.service.PokerRoomService;
import pl.coderslab.pokersessionmanager.service.RedirectService;
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
        model.addAttribute("pokerRoomSlimList",
                pokerRoomService.findAllSlimByScope(pokerRoomScope));

        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addPokerRoomGet(Model model) {
        model.addAttribute("pokerRoomSlim", Factory.create(PokerRoomSlim.class));

        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/add")
    public String addPokerRoomPost(@Valid PokerRoomSlim pokerRoomSlim,
                                   BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.create(pokerRoomSlim);

        return redirectService.setRedirectAfterCreatingPokerRoom();
    }

    @GetMapping("/edit/{id}")
    public String editPokerRoomGet(@PathVariable Long id,
                                   Model model) {
        model.addAttribute("pokerRoomSlim", pokerRoomService.findSlimById(id));

        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/edit/{id}")
    public String editPokerRoomPost(@Valid PokerRoomSlim pokerRoomSlim,
                                    BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.edit(pokerRoomSlim);

        return redirectService.setRedirectAfterProcessingPokerRoomSlim(pokerRoomSlim.getId());
    }

    @GetMapping("/delete/{id}")
    public String deletePokerRoom(@PathVariable Long id) {
        String redirectUrl = redirectService.setRedirectAfterProcessingPokerRoomSlim(id);
//        PokerRoomSlim pokerRoomSlim = pokerRoomService.findSlimById(id);
        pokerRoomService.delete(id);

        return redirectUrl;
    }
}
