package pl.coderslab.pokersessionmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal CurrentUser loggedUser) {
        if (loggedUser == null) {

            return "login/loginForm";
        }
        return "redirect:/";
    }
    
}
