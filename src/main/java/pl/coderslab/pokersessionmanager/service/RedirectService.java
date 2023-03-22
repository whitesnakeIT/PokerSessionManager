package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;

import static pl.coderslab.pokersessionmanager.enums.RoleName.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RedirectService {

    private final UserService userService;
    private final PokerRoomService pokerRoomService;
    private final SessionService sessionService;

    public String getRedirectEmptyUrl() {
        if (userService.hasAuthority(ROLE_ADMIN)) {
            return "redirect:/app/admin/dashboard";
        } else if (userService.hasAuthority(ROLE_USER)) {
            return "redirect:/app/player/dashboard";
        } else if (userService.hasAuthority(ROLE_ANONYMOUS))
            return "redirect:/login";

        throw new RuntimeException("Getting redirect url on / address failed. Unrecognized authority.");
    }

    public String getRedirectLoginPage() {
        if (userService.hasAuthority(ROLE_ANONYMOUS)) {
            return "authorization/login/loginForm";
        } else
            return "redirect:/";
    }

    public String getRedirectForPokerRoomListByRole() {
        PokerRoomScope scope = pokerRoomService.getScopeByRole();
        return String.format("redirect:/app/poker_room/%s/all", scope);
    }

    public String getRedirectAfterProcessingPokerRoomSlim(Long pokerRoomSlimId) {
        if (pokerRoomSlimId == null) {
            throw new RuntimeException("Getting poker room redirect url failed. Poker room slim id is null.");
        }
        PokerRoom pokerRoom = pokerRoomService.findById(pokerRoomSlimId);

        if (ifAdminProcessingWithPlayerPokerRoom(pokerRoom)) {
            Long pokerRoomOwnerId = pokerRoom.getPlayer().getId();
            return String.format("redirect:/app/admin/users/details/%d", pokerRoomOwnerId);
        }

        return String.format("redirect:/app/poker_room/%s/all", pokerRoom.getPokerRoomScope());
    }

    public String getRedirectAfterProcessingSession(Long sessionId) {
        if (sessionId == null) {
            throw new RuntimeException("Setting session redirect url failed. Session id is null.");
        }
        if (userService.hasAuthority(ROLE_USER)) {
            return "redirect:/app/session/all";

        } else if (userService.hasAuthority(ROLE_ADMIN)) {
            Long sessionOwnerId = sessionService.findById(sessionId).getPlayer().getId();
            return String.format("redirect:/app/admin/users/details/%d", sessionOwnerId);
        }
        throw new RuntimeException("Setting session redirect url failed. Authority is not recognized");
    }

    private boolean ifAdminProcessingWithPlayerPokerRoom(PokerRoom pokerRoom) {
        return pokerRoomService.hasOwner(pokerRoom) && userService.hasAuthority(ROLE_ADMIN);
    }
}
