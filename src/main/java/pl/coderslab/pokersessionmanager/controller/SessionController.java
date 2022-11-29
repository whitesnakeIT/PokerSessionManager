package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.SessionService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;

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
    public String editSessionPost(@Valid Session session, BindingResult result) {
        if (result.hasErrors()) {
            return "user/session/sessionForm";
        }
        Session oldSession = sessionService.findById(session.getId());
        oldSession.setName(session.getName());

        sessionService.create(oldSession);
        return "redirect:/app/user/session/all";
    }

    @GetMapping("/user/session/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.delete(id);
        return "redirect:/app/user/session/all";

    }


}
