package pl.coderslab.pokersessionmanager.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.UtilityService;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CurrentUser principal = (CurrentUser) authentication.getPrincipal();

        UtilityService utilityService;
        String redirectUrl = request.getContextPath();

        if (principal.hasRole(RoleName.ROLE_ADMIN)) {
            redirectUrl += "admin/dashboard";
        } else if (principal.hasRole(RoleName.ROLE_USER)) {
            redirectUrl += "app/dashboard";
        }

        response.sendRedirect(redirectUrl);
    }
}
