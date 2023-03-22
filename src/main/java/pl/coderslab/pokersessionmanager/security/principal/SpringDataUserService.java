package pl.coderslab.pokersessionmanager.security.principal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.service.RoleService;
import pl.coderslab.pokersessionmanager.service.UserService;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpringDataUserService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles()
                .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().toString())));
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }

    public User getLoggedUser() {
        return userService
                .findById(
                        (
                                (CurrentUser)
                                        SecurityContextHolder
                                                .getContext()
                                                .getAuthentication()
                                                .getPrincipal()
                        )
                                .getUser()
                                .getId());
    }

    public Long getLoggedUserId() {
        return getLoggedUser().getId();
    }

    public Collection<? extends GrantedAuthority> getLoggedUserAuthority() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
 public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isLoggedAsAdmin() {
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_ADMIN));
    }

    public boolean isLoggedAsUser() {
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_USER));
    }

    public boolean isAnonymous() {
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_ANONYMOUS));
    }

    public boolean hasAuthority(RoleName roleName) {
        return getLoggedUserAuthority().contains(roleService.getGrantedAuthority(roleName));
    }

}
