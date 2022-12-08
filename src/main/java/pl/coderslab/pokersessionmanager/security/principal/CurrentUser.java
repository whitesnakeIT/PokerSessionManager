package pl.coderslab.pokersessionmanager.security.principal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
public class CurrentUser extends User {
    private final pl.coderslab.pokersessionmanager.entity.user.User user;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.pokersessionmanager.entity.user.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public boolean hasRole(String roleName){
        return this.user.hasRole(roleName);
    }
}
