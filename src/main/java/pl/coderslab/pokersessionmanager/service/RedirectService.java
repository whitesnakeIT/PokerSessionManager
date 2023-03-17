package pl.coderslab.pokersessionmanager.service;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.ClassNamesForFactory;
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
        System.out.println();
        System.out.println("sendRedirectLoginPage");

        Collection<? extends GrantedAuthority> authorities =
                userService.getLoggedUserAuthority();

        if (authorities.contains(
                Factory.create(
                        RoleName.ROLE_ANONYMOUS))) {
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


    public String setRedirectAfterProcessingPokerRoomSlim(Long pokerRoomId) {

//        if (userService.isLoggedAsUser()) {
//            return ("redirect:/app/poker_room/local/all");
//        } else if (userService.isLoggedAsAdmin()) {
//            Optional<Player> optionalOwner = pokerRoomService.findPokerRoomOwner(pokerRoomId);
//            if (optionalOwner.isEmpty()) {
//                return "redirect:/app/poker_room/global/all";
//            } else {
//                return String.format("redirect:/app/admin/users/details/%d", optionalOwner.get().getId());
//            }
//        }
        // not for deleting...

        PokerRoom pokerRoom = pokerRoomService.findById(pokerRoomId);

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

        throw new RuntimeException("I can't set redirect after processing Poker Room.");
    }

    public <T> String sendRedirectAfterEditingEntity(Class<T> clazz, @Nullable User owner) {
        User loggedUser = userService.getLoggedUser();
        PokerRoomScope scope = owner != null ? PokerRoomScope.LOCAL : PokerRoomScope.GLOBAL;
        if (clazz.getSimpleName().equals(ClassNamesForFactory.POKER_ROOM.toString())) {

            // If admin is editing user Entity
            if (loggedUser.hasRole(RoleName.ROLE_ADMIN) && owner != null) {

                return "redirect:/app/admin/users/details/".concat(String.valueOf(owner.getId()));
            } else {

                // If User or admin are editing entities
                return "redirect:/app/poker_room/".concat(scope.toString()).concat("/all");
            }
        }
        if (clazz.getSimpleName().equals(ClassNamesForFactory.SESSION.toString())) {
            if (loggedUser.hasRole(RoleName.ROLE_ADMIN) && owner != null) {
                return "redirect:/app/admin/users/details/".concat(String.valueOf(owner.getId()));
            } else {

// If User or admin are editing entities
                return "redirect:/app/session/all";
            }
        }
        throw new RuntimeException("I can't send redirect");

    }

}
