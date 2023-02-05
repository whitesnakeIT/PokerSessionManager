package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.pokersessionmanager.enums.RoleName;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String showIndex() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()))) {
            return "admin/adminPanel";
        } else if (authorities.contains(new SimpleGrantedAuthority(RoleName.ROLE_USER.name()))) {
            return "player/dashboard";
        }

        return "redirect:/login";
    }
}
