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

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    private final RedirectService redirectService;

    @GetMapping("/registration")
    public String registerNewPlayerGet(Model model) {
        return redirectService.sendRedirectRegistrationPage(model);
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
