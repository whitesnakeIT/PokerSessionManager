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
import pl.coderslab.pokersessionmanager.enums.UserType;
import pl.coderslab.pokersessionmanager.service.UserService;
import pl.coderslab.pokersessionmanager.utilities.Factory;

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registerNewPlayerGet(Model model) {
        model.addAttribute("newPlayer", Factory.create(UserType.PLAYER));

        return "registration/registrationForm";
    }

    @PostMapping("/registration")
    public String registrationNewPlayerPost(@Valid @ModelAttribute(name = "newPlayer") Player newPlayer,
                                            BindingResult result,
                                            @RequestParam(name = "passwordCheck") String passwordCheck,
                                            Model model) {
        if (result.hasErrors()) {

            return "registration/registrationForm";
        }
        if (!passwordCheck.equals(newPlayer.getPassword())) {
            model.addAttribute("isCorrectPass", false);

            return "registration/registrationForm";
        }
        model.addAttribute("isCorrectPass", true);
        userService.create(newPlayer);

        return "redirect:/";
    }
}
