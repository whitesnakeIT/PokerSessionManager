package pl.coderslab.pokersessionmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.service.RedirectService;
import pl.coderslab.pokersessionmanager.service.UserService;

import static pl.coderslab.pokersessionmanager.enums.RoleName.ROLE_ANONYMOUS;

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registerNewPlayerGet(Model model) {
        if (userService.hasAuthority(ROLE_ANONYMOUS)) {
            model.addAttribute("newPlayer", new Player());
            return "authorization/registration/registrationForm";
        }
        return "redirect:/";
    }

    @PostMapping("/registration")
    public String registrationNewPlayerPost(@Valid @ModelAttribute(name = "newPlayer") Player newPlayer,
                                            BindingResult result,
                                            @RequestParam(name = "passwordCheck") String passwordCheck,
                                            Model model) {
        if (result.hasErrors()) {
            return "authorization/registration/registrationForm";

        } else if (!passwordCheck.equals(newPlayer.getPassword())) {
            model.addAttribute("isCorrectPass", false);
            return "authorization/registration/registrationForm";

        } else {
            model.addAttribute("isCorrectPass", true);
        }
        userService.create(newPlayer);

        return "redirect:/";
    }
}
