package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithOutPassword;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithPassword;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.UserMapper;
import pl.coderslab.pokersessionmanager.repository.UserRepository;

//import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional()
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    private final PlayerService playerService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return ifUserExist(userOptional);
    }

    public void loadRolesToUser(User user) {
//        Hibernate.initialize(user.getRoles());
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
        return ifUserExist(userOptional);
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

    public boolean isPasswordCorrect(String passwordToCheck, String userPassword) {

        return bCryptPasswordEncoder.matches(passwordToCheck, userPassword);
    }

    public boolean updatePassword(User user, String oldPassword, String newPassword, String confirmNewPassword) {

        boolean compareActualAndOldPassword = bCryptPasswordEncoder.matches(oldPassword, user.getPassword());


        boolean compareActualAndNewPassword = bCryptPasswordEncoder.matches(newPassword, user.getPassword());
        boolean compareActualAndConfirmNewPassword = bCryptPasswordEncoder.matches(confirmNewPassword, user.getPassword());


        boolean compareNewPasswordAndConfirmNewPassword = newPassword.equals(confirmNewPassword);

        if ((oldPassword.equals("")) || (newPassword.equals("")) || (confirmNewPassword.equals(""))) {
            System.out.println("jakies polle puste");
            return false;
        }

        if (!compareActualAndOldPassword) {
            System.out.println("stare haslo bledne");
            return false;
        } else if (compareActualAndNewPassword || compareActualAndConfirmNewPassword) {
            System.out.println("Stare ok ale nowe lub confirm jak stare");
            return false;
        }

        if (!compareNewPasswordAndConfirmNewPassword) {
            System.out.println("Stare ok ale 2 rozne");
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;

    }

    public UserSlimWithPassword convertUserToUserSlimWithPassword(User user) {
        return userMapper.userToUserToUserSlimWithPassword(user);
    }

    public UserSlimWithOutPassword convertUserToUserSlimWithOutPassword(User user) {
        return userMapper.userToUserToUserSlimWithOutPassword(user);
    }

    public void update(User user, String firstName, String lastName, String username) {
        userRepository.update(user, firstName, lastName, username);
    }
}
