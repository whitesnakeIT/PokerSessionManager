package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.repository.RoleRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
