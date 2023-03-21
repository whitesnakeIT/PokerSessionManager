package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.enums.UserType;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class RedirectService {

    private final UserService userService;
    private final PokerRoomService pokerRoomService;
    private final SessionService sessionService;

    public String sendRedirectEmptyUrl() {

        if (userService.isLoggedAsAdmin()) {

            return "redirect:/app/admin/dashboard";

        }
        if (userService.isLoggedAsUser()) {

            return "redirect:/app/player/dashboard";
        }

        return "redirect:/login";
    }

    public String sendRedirectLoginPage() {
        if (userService.isAnonymous()) {
            return "authorization/login/loginForm";
        }

        return "redirect:/";
    }

    public String sendRedirectRegistrationPage(Model model) {
        Collection<? extends GrantedAuthority> authorities =
                userService.getLoggedUserAuthority();

        if (authorities.contains(Factory.create(RoleName.ROLE_ANONYMOUS))) {
            model.addAttribute("newPlayer", Factory.create(UserType.PLAYER));
            return "authorization/registration/registrationForm";
        } else {
            return "redirect:/";
        }
    }

    public String setRedirectAfterCreatingPokerRoom() {
        PokerRoomScope scope = pokerRoomService.setPokerRoomScopeByRole();
        return String.format("redirect:/app/poker_room/%s/all", scope);
    }


    public String setRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId) {
        if (pokerRoomSlimId == null) {
            throw new RuntimeException("Setting poker room redirect url failed. Poker room slim id is null.");
        }
//        if (userService.isLoggedAsUser()) {
//            return ("redirect:/app/poker_room/local/all");
//        } else if (userService.isLoggedAsAdmin()) {
//            Optional<Player> optionalOwner = pokerRoomService.findPokerRoomOwner(pokerRoomSlimId);
//            if (optionalOwner.isEmpty()) {
//                return "redirect:/app/poker_room/global/all";
//            } else {
//                return String.format("redirect:/app/admin/users/details/%d", optionalOwner.get().getId());
//            }
//        }
        // not for deleting...

        PokerRoom pokerRoom = pokerRoomService.findById(pokerRoomSlimId);

        switch (pokerRoom.getPokerRoomScope()) {

            case LOCAL -> {
                if (userService.isLoggedAsUser()) {
                    return "redirect:/app/poker_room/local/all";
                } else if (userService.isLoggedAsAdmin()) {
                    Long pokerRoomOwnerId = pokerRoom.getPlayer().getId();
                    return String.format("redirect:/app/admin/users/details/%d", pokerRoomOwnerId);
                }
            }
            case GLOBAL -> {
                return "redirect:/app/poker_room/global/all";
            }
        }

        throw new RuntimeException("Setting poker room redirect url failed.");
    }

    public String setRedirectAfterProcessingSession(Long sessionId) {
        if (sessionId == null) {
            throw new RuntimeException("Setting session redirect url failed. Session id is null.");
        }
        Long sessionOwnerId = sessionService.findById(sessionId).getPlayer().getId();
        if (userService.isLoggedAsUser()) {

            return "redirect:/app/session/all";
        } else if (userService.isLoggedAsAdmin()) {
            return String.format("redirect:/app/admin/users/details/%d", sessionOwnerId);
        }
        throw new RuntimeException("Setting session redirect url failed.");
    }
}
