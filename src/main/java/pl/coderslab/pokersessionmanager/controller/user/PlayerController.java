package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class PlayerController {

    private final UserService userService;

    @GetMapping("/player/show-stats")
    public String showPlayerStats(Model model) {
        User player = userService.getLoggedUser();
        model.addAttribute("player", player);

        return "player/playerStats";
    }
}

