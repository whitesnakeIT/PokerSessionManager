package pl.coderslab.pokersessionmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.SessionService;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

//import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/session")
public class SessionController {

    private final SessionService sessionService;
    private final TournamentService tournamentService;

    private final UserService userService;

    @GetMapping("/all")
    public String showAllSessions(Model model) {

        User player = userService.getLoggedUser();
        model.addAttribute("allUserSessions",
                sessionService.findAllUserSessions(player.getId()));

        return "player/session/allSessionList";
    }

    @GetMapping("/add")
    public String addSessionGet(Model model) {
        model.addAttribute("session", new Session());

        return "player/session/sessionForm";
    }

    @PostMapping("/add")
    public String addSessionPost(@Valid Session session,
                                 BindingResult result) {
        if (result.hasErrors()) {

            return "player/session/sessionForm";
        }
        sessionService.create(session);

        return "redirect:/app/session/all";

    }


    @GetMapping("/edit/{sessionId}")
    public String editSessionGet(@PathVariable Long sessionId,
                                 Model model) {
                model.addAttribute(sessionService.findById(sessionId));

        return "player/session/sessionForm";
    }

    @PostMapping("/edit/{id}")
    public String editSessionPost(@Valid Session session,
                                  BindingResult result) {
        if (result.hasErrors()) {

            return "player/session/sessionForm";
        }
        sessionService.create(session);

        return "redirect:/app/session/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.delete(id);

        return "redirect:/app/session/all";
    }

    @ModelAttribute("availableSessionTournaments")
    public List<AbstractTournament> getAvailableTournamentsForSessionOrderByFavourites(@AuthenticationPrincipal CurrentUser loggedUser) {
        return tournamentService.getAvailableTournamentsForSessionOrderByFavourites(loggedUser.getUser().getId());
    }

//    @ModelAttribute("availableSessionTournamentsSlim")
//    public List<TournamentSlimDto> getAvailableTournamentsForSessionOrderByFavouritesSlim(@AuthenticationPrincipal CurrentUser loggedUser) {
//
//        List<TournamentGlobal> availableTournamentsForSession = tournamentService.getAvailableTournamentsForSessionOrderByFavourites(loggedUser.getUser().getId());
//        List<TournamentSlimDto> sessionSlimTournaments = availableTournamentsForSession
//                .stream()
//                .map(tournamentService::convertTournamentToSlimDto)
//                .collect(Collectors.toList());
//        return sessionSlimTournaments;
//
//    }

//    @ModelAttribute("userFavouriteTournamentSize")
//    public Integer getUserFavouriteTournamentSize(@AuthenticationPrincipal CurrentUser loggedUser) {
//        Integer favouriteTournamentSize = userService.findById(loggedUser.getUser().getId())
//                .getFavouriteTournaments().size();
//        return favouriteTournamentSize;
//
//    }

}

