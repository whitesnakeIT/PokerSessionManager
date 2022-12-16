package pl.coderslab.pokersessionmanager.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.PlayerService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpringDataUserService implements UserDetailsService {

    private final UserService userService;

    private final PlayerService playerService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles()
                .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }

}
