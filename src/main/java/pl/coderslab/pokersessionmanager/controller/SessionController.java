package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.SessionService;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;

    private final TournamentService tournamentService;

    @GetMapping("/user/session/all")
    public String showAllSessions(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<Session> allUserSessions = sessionService.findAllUserSessions(user.getId());

        model.addAttribute("allUserSessions", allUserSessions);
        return "user/session/allSessionList";
    }

    @GetMapping("/user/session/add")
    public String addSessionGet(Model model) {

        model.addAttribute("session", new Session());
        return "user/session/sessionForm";
    }

    @PostMapping("/user/session/add")
    public String addSessionPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                 @Valid Session session,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "user/session/sessionForm";
        }
        User user = userService.findById(loggedUser.getUser().getId());
        session.setUser(user);
        session.setTournamentCount(session.getSessionTournaments().size());
        session.setTotalCost(session.getSessionTournaments().stream().mapToDouble(AbstractTournament::getBuyIn).sum());
        sessionService.create(session);
        return "redirect:/app/user/session/all";

    }


    @GetMapping("/user/session/edit/{sessionId}")
    public String editSessionGet(@PathVariable Long sessionId, Model model) {
        Session session = sessionService.findById(sessionId);
        model.addAttribute(session);
        return "user/session/sessionForm";
    }

    @PostMapping("user/session/edit/{id}")
    public String editSessionPost(@Valid Session session, BindingResult result, @AuthenticationPrincipal CurrentUser loggedUser) {
        if (result.hasErrors()) {
            return "user/session/sessionForm";
        }
        session.setUser(userService.findById(loggedUser.getUser().getId()));
        sessionService.create(session);
        return "redirect:/app/user/session/all";
    }

    @GetMapping("/user/session/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.delete(id);
        return "redirect:/app/user/session/all";

    }

//    @ModelAttribute("availableSessionTournaments")
//    public List<TournamentGlobal> getAvailableTournamentsForSessionOrderByFavourites(@AuthenticationPrincipal CurrentUser loggedUser) {
//        return tournamentService.getAvailableTournamentsForSessionOrderByFavourites(loggedUser.getUser().getId());
//    }
//
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

    @ModelAttribute("userFavouriteTournamentSize")
    public Integer getUserFavouriteTournamentSize(@AuthenticationPrincipal CurrentUser loggedUser) {
        Integer favouriteTournamentSize = userService.findById(loggedUser.getUser().getId())
                .getFavouriteTournaments().size();
        return favouriteTournamentSize;

    }

}

