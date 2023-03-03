package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.pokersessionmanager.service.RedirectService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RedirectService redirectService;

    @GetMapping("/")
    public String showIndex() {
        return redirectService.sendRedirectEmptyUrl();
    }
}
