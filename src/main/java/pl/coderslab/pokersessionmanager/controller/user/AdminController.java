package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/admin")
public class AdminController {

    private final UserService userService;

    private final TournamentService tournamentService;

    private final SessionService sessionService;

    private final PlayerService playerService;

    private final PokerRoomService pokerRoomService;

    //do usuniecia zwyklych czy adminow tez

    @GetMapping("/users/all")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userService.findAll());

        return "admin/usersList";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.delete(userId);

        return "redirect:/app/admin/users/all";
    }

    @GetMapping("/users/details/{playerId}")
    public String showUserById(@PathVariable Long playerId,
                               Model model) {
        model.addAttribute("player", playerService.findById(playerId));

        return "admin/userDetails";
    }

    @GetMapping("/users/details/{userId}/tournament/delete/{tournamentId}")
    public String deleteUserTournamentAsAdmin(@PathVariable Long userId,
                                              @PathVariable Long tournamentId) {
        tournamentService.delete(tournamentId);

        return "redirect://appadmin/users/details/".concat(String.valueOf(userId));
    }


//    @GetMapping("/users/details/{playerId}/session/edit/{sessionId}")
//    public String editUserSessionAsAdminGet(@PathVariable Long playerId,
//                                            @PathVariable Long sessionId,
//                                            Model model) {
//        model.addAttribute(sessionService.findById(sessionId));
//        model.addAttribute("availableSessionTournaments",
//                tournamentService.getAvailableTournamentsForSessionOrderByFavourites(playerId));
//
//        return "/session/sessionForm";
//    }
//
//    @PostMapping("/users/details/{playerId}/session/edit/{id}")
//    public String editUserSessionAsAdminPost(@PathVariable Long playerId,
//                                             @Valid Session session,
//                                             BindingResult result) {
//        if (result.hasErrors()) {
//            return "/session/sessionForm";
//        }
//
//        session.setPlayer(playerService.findById(playerId));
//        sessionService.create(session);
//
//        return "redirect:/admin/users/details/".concat(String.valueOf(playerId));
//    }
//
//    @GetMapping("/users/details/{playerId}/session/delete/{sessionId}")
//    public String deleteUserSessionAsAdmin(@PathVariable Long playerId,
//                                           @PathVariable Long sessionId) {
//        sessionService.delete(sessionId);
//
//        return "redirect:/admin/users/details/".concat(String.valueOf(playerId));
//    }
//
//    @GetMapping("/users/details/{playerId}/poker_room/edit/{pokerRoomId}")
//    public String editUserPokerRoomAsAdminGet(@PathVariable Long playerId,
//                                              @PathVariable Long pokerRoomId,
//                                              Model model) {
//        model.addAttribute(pokerRoomService.findById(pokerRoomId));
//
//        return "poker_room/pokerRoomForm";
//    }
//
//    @PostMapping("/users/details/{playerId}/poker_room/edit/{id}")
//    public String editUserPokerRoomAsAdminPost(@PathVariable Long playerId,
//                                               @Valid PokerRoom pokerRoom,
//                                               BindingResult result) {
//        if (result.hasErrors()) {
//            return "poker_room/pokerRoomForm";
//        }
//        pokerRoomService.create(pokerRoom);
//
//        return "redirect:/admin/users/details/".concat(String.valueOf(playerId));
//    }
//
//    @GetMapping("/users/details/{playerId}/poker_room/delete/{pokerRoomId}")
//    public String deleteUserPokerRoomAsAdmin(@PathVariable Long playerId,
//                                             @PathVariable Long pokerRoomId) {
//        pokerRoomService.delete(pokerRoomId);
//
//        return "redirect:/admin/users/details/".concat(String.valueOf(playerId));
//    }

}