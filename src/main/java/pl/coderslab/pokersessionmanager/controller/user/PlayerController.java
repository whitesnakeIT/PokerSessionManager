package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PlayerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class PlayerController {



    @GetMapping("/player/show-stats")
    public String showPlayerStats(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        Player player = (Player) loggedUser.getUser();
        model.addAttribute("player", player);

        return "player/playerStats";

    }
}

