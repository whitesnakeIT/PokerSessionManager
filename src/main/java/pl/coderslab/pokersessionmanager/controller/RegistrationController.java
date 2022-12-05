package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String registrationNewUserPost(@Valid @ModelAttribute(name = "newUser") User newUser, BindingResult result) {
        if (result.hasErrors()) {
            return "registration/registrationForm";
        }
        userService.create(newUser);

        return "redirect:/";
    }
}
