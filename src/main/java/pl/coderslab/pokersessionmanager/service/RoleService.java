package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
}
