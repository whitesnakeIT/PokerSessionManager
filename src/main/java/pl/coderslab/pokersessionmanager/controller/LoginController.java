package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    // pytanie nr 1 Czy jesli wszedzie daje user service do pobrania zalogowanego uzytkownika
    // a tutaj uzyje przez adnotacje to jest blad ? znalezc ine rozwiazanie?
    // private final UserService userService;
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal CurrentUser loggedUser) {
//    public String login() {
//        Optional<User>loggedUser = Optional.ofNullable(userService.getLoggedUser());
//        if (loggedUser.isEmpty()) {
        if (loggedUser==null) {

            return "login/loginForm";
        }


        return "redirect:/";
    }
}
