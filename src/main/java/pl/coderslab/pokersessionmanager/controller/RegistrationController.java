package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registerNewUserGet(@ModelAttribute(name = "newUser") User newUser) {
        return "registration/registrationForm";
    }

    @PostMapping("/registration")
    public String registrationNewUserPost(@Valid @ModelAttribute(name = "newUser") User newUser, BindingResult result,
                                          @RequestParam(name = "passwordCheck") String passwordCheck, Model model) {
        if (result.hasErrors()) {
            return "registration/registrationForm";
        }
        if (!passwordCheck.equals(newUser.getPassword())) {
            model.addAttribute("isCorrectPass", false);
            return "registration/registrationForm";
        }
        model.addAttribute("isCorrectPass", true);
        userService.create(newUser);

        return "redirect:/";
    }
}
