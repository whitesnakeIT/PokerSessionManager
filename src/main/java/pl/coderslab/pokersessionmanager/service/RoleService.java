package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.RoleRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(RoleName roleName) {
        if (roleName == null) {
            throw new RuntimeException("Searching for role failed. Role name is null.");
        }
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Searching for role failed. Unrecognized roleName: " + roleName));
    }
    public SimpleGrantedAuthority getGrantedAuthority(RoleName roleName) {
        switch (roleName) {
            case ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR, ROLE_ANONYMOUS -> {
                return new SimpleGrantedAuthority(roleName.name());
            }
            default ->
                    throw new IllegalStateException("I can't create SimpleGrantedAuthority. Unexpected value: " + roleName);
        }
    }
}
