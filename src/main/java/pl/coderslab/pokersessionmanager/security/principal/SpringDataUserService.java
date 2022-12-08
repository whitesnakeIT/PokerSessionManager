package pl.coderslab.pokersessionmanager.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpringDataUserService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(username);
//        Optional<User> userOptional = userService.findByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            user.getRoles()
                    .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
            return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
        }
        throw new UsernameNotFoundException(username);
    }
}
