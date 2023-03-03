package pl.coderslab.pokersessionmanager.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.pokersessionmanager.service.RedirectService;
import pl.coderslab.pokersessionmanager.service.UserService;

@Controller
@RequiredArgsConstructor
@Transactional
public class LoginController {
    private final RedirectService redirectService;
    private final UserService userService;

    @GetMapping("/login")

    public String login() {
        return redirectService.sendRedirectLoginPage();
    }
}
