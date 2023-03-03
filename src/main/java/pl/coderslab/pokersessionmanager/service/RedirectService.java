package pl.coderslab.pokersessionmanager.service;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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

    public String sendRedirectEmptyUrl() {
        Collection<? extends GrantedAuthority> authorities =
                userService.getLoggedUserAuthority();

        if (authorities.contains
                (Factory.create(RoleName.ROLE_ADMIN))) {

            return "redirect:/app/admin/dashboard";

        } else if (authorities.contains(
                Factory.create(RoleName.ROLE_USER))) {

            return "redirect:/app/player/dashboard";
        }

        return "redirect:/login";
    }

    public String sendRedirectLoginPage() {
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

    public <T> String sendRedirectAfterEditingEntity(Class<T> clazz, @Nullable User owner) {
        User loggedUser = userService.getLoggedUser();
        PokerRoomScope scope = owner!= null ? PokerRoomScope.LOCAL : PokerRoomScope.GLOBAL;
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
