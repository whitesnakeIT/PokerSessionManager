package pl.coderslab.pokersessionmanager.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.io.IOException;
import java.util.Collection;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

//        CurrentUser principal = (CurrentUser) authentication.getPrincipal();
//        String redirectUrl = request.getContextPath();
//
//        if (principal.hasRole(RoleName.ROLE_ADMIN)) {
//            redirectUrl += "admin/dashboard";
//        } else if (principal.hasRole(RoleName.ROLE_USER)) {
//            redirectUrl += "app/player/dashboard";
//        }

//        response.sendRedirect(redirectUrl);
//    }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = request.getContextPath();
//        System.out.println(redirectUrl);
        if (authorities.contains(Factory.create(RoleName.ROLE_ADMIN))) {
            redirectUrl = redirectUrl.concat("app/admin/dashboard");
        } else if (authorities.contains(Factory.create(RoleName.ROLE_USER))) {
            redirectUrl = redirectUrl.concat("app/player/dashboard");
        }

        response.sendRedirect(redirectUrl);
    }
}
