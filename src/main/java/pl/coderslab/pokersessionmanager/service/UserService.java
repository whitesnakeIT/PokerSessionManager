package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    private final PlayerService playerService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            loadRolesToUser(user);
//            if (user.hasRole(RoleName.ROLE_USER.name())) {
//                playerService.loadLazyDataToPlayer((Player) user);
//            }
//            return user;
        return ifUserExist(userOptional);
//        }
//        throw new RuntimeException("I can't find player by email.");
    }

    public void loadRolesToUser(User user) {
        Hibernate.initialize(user.getRoles());
    }

    // jedna klasa
    public <T extends User> void create(T user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role role = roleService.findByName(RoleName.ROLE_USER.name());
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            loadRolesToUser(user);
//            if (user.hasRole(RoleName.ROLE_USER.name())) {
//                playerService.loadLazyDataToPlayer((Player) user);
//            }
//            return user;
//
//        }
        return ifUserExist(userOptional);
//        throw new RuntimeException("I can't find player by id.");
    }

    public void delete(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

    public User ifUserExist(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            loadRolesToUser(user);
            if (user.hasRole(RoleName.ROLE_USER.name())) {
                playerService.loadLazyDataToPlayer((Player) user);
            }
            return user;

        }
        throw new RuntimeException("User not exist");
    }
}
