package pl.coderslab.pokersessionmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.RedirectService;
import pl.coderslab.pokersessionmanager.service.SessionService;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/session")
public class SessionController {

    private final SessionService sessionService;
    private final TournamentService tournamentService;
    private final UserService userService;
    private final RedirectService redirectService;

    @GetMapping("/all")
    public String showAllSessions(Model model) {
        User player = userService.getLoggedUser();
        model.addAttribute("allUserSessions",
                sessionService.findSessionsByPlayerId(player.getId()));

        return "session/allSessionList";
    }

    @GetMapping("/add")
    public String addSessionGet(Model model) {
        model.addAttribute("session", new Session());

        return "session/sessionForm";
    }

    @PostMapping("/add")
    public String addSessionPost(@Valid Session session,
                                 BindingResult result) {
        if (result.hasErrors()) {

            return "session/sessionForm";
        }
        sessionService.create(session);

        return "redirect:/app/session/all";
    }

    @GetMapping("/edit/{id}")
    public String editSessionGet(@PathVariable(name = "id") Long sessionId,
                                 Model model) {
        model.addAttribute(sessionService.findById(sessionId));

        return "session/sessionForm";
    }

    @PostMapping("/edit/{id}")
    public String editSessionPost(@Valid Session session,
                                  BindingResult result) {
        if (result.hasErrors()) {

            return "/session/sessionForm";
        }
        sessionService.edit(session);

        return redirectService.setRedirectAfterProcessingSession(session.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable(name = "id") Long sessionId) {
        String redirectUrl = redirectService.setRedirectAfterProcessingSession(sessionId);
        sessionService.delete(sessionId);

        return redirectUrl;
    }

    @ModelAttribute("availableSessionTournaments")
    public List<AbstractTournament> getAvailableTournamentsForSessionOrderByFavourites() {
        return tournamentService.
                getAvailableTournamentsForSessionOrderByFavourites(userService.getLoggedUserId());
    }
}

