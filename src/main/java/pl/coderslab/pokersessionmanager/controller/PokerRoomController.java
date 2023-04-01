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

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/poker_room/{pokerRoomScope}")
public class PokerRoomController {

    private final PokerRoomService pokerRoomService;

    private final RedirectService redirectService;

    @GetMapping("/all")
    public String showAllPokerRooms(@PathVariable(name = "pokerRoomScope") PokerRoomScope pokerRoomScope,
                                    Model model) {
        model.addAttribute("pokerRoomSlimList",
                pokerRoomService.findAllSlimByScope(pokerRoomScope));

        return "poker_room/pokerRoomList";
    }

    @GetMapping("/add")
    public String addPokerRoomGet(Model model) {
        model.addAttribute("pokerRoomSlim", new PokerRoomSlim());

        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/add")
    public String addPokerRoomPost(@Valid PokerRoomSlim pokerRoomSlim,
                                   BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.create(pokerRoomSlim);

        return redirectService.getRedirectForPokerRoomListByRole();
    }

    @GetMapping("/edit/{id}")
    public String editPokerRoomGet(@PathVariable(name = "id") Long pokerRoomSlimId,
                                   Model model) {
        model.addAttribute("pokerRoomSlim", pokerRoomService.findSlimById(pokerRoomSlimId));

        return "poker_room/pokerRoomForm";
    }

    @PostMapping("/edit/{id}")
    public String editPokerRoomPost(@Valid PokerRoomSlim pokerRoomSlim,
                                    BindingResult result) {
        if (result.hasErrors()) {

            return "poker_room/pokerRoomForm";
        }
        pokerRoomService.edit(pokerRoomSlim);

        return redirectService.getRedirectAfterProcessingPokerRoomSlim(pokerRoomSlim.getId());
    }

    @GetMapping("/delete/{id}")
    public String deletePokerRoom(@PathVariable(name = "id") Long pokerRoomSlimId) {
        String redirectUrl = redirectService.getRedirectAfterProcessingPokerRoomSlim(pokerRoomSlimId);
//        PokerRoomSlim pokerRoomSlim = pokerRoomService.findSlimById(id);
        pokerRoomService.delete(pokerRoomSlimId);

        return redirectUrl;
    }
}
