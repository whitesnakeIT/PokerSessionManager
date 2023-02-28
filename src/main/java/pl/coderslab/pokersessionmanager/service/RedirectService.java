package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class RedirectService {

    private final UserService userService;

    public String sendRedirectAfterLoginByRole() {
        Collection<? extends GrantedAuthority> authorities =
                userService.getLoggedUserAuthority();

        if (authorities.contains
                (Factory.create(RoleName.ROLE_ADMIN))) {

            return "admin/adminPanel";

        } else if (authorities.contains(
                Factory.create(RoleName.ROLE_USER))) {

            return "player/dashboard";
        }

        return "redirect:/login";
    }

    public String sendRedirectBeforeLogin() {
        Collection<? extends GrantedAuthority> authorities =
                userService.getLoggedUserAuthority();

        if (authorities.contains(
                Factory.create(
                        RoleName.ROLE_ANONYMOUS))) {

            return "login/loginForm";
        }

        return "redirect:/";
    }
}
