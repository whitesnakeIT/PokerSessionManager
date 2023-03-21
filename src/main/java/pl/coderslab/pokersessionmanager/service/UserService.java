package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PasswordErrors;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithOutPassword;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithPassword;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.UserMapper;
import pl.coderslab.pokersessionmanager.repository.UserRepository;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.utilities.Factory;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    private final PlayerService playerService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;


    public User findByEmail(String email) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return ifUserExist(userOptional);
    }

    public void loadRolesToUser(User user) {
        Hibernate.initialize(user.getRoles());
    }

    // jedna klasa
    public <T extends User> void create(T user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        user.addRole(roleService.getAdminRole());
        userRepository.save(user);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        if (userId == null) {
            throw new RuntimeException("Searching player failed. User id is null.");
        }
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
            if (user.hasRole(RoleName.ROLE_USER)) {
                playerService.loadLazyDataToPlayer((Player) user);
            }
            return user;

        }
        throw new RuntimeException("User not exist");
    }

    public boolean isPasswordCorrect(String passwordToCheck, String userPassword) {

        return bCryptPasswordEncoder.matches(passwordToCheck, userPassword);
    }

    public PasswordErrors updatePassword(User user, String oldPassword, String newPassword, String confirmNewPassword) {

        boolean compareActualAndOldPassword = bCryptPasswordEncoder.matches(oldPassword, user.getPassword());

        boolean compareActualAndNewPassword = bCryptPasswordEncoder.matches(newPassword, user.getPassword());
        boolean compareActualAndConfirmNewPassword = bCryptPasswordEncoder.matches(confirmNewPassword, user.getPassword());

        boolean compareNewPasswordAndConfirmNewPassword = newPassword.equals(confirmNewPassword);

        if ((oldPassword.equals("")) || (newPassword.equals("")) || (confirmNewPassword.equals(""))) {

            return PasswordErrors.EMPTY_INPUT;
        }

        if (!compareActualAndOldPassword) {

            return PasswordErrors.OLD_PASSWORD_WRONG;
        } else if (compareActualAndNewPassword || compareActualAndConfirmNewPassword) {

            return PasswordErrors.NEW_PASSWORD_SAME_AS_OLD;
        }
        if (!compareNewPasswordAndConfirmNewPassword) {

            return PasswordErrors.PASSWORDS_NOT_MATCH;
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        return PasswordErrors.NO_ERROR;
    }

    public UserSlimWithPassword convertUserToUserSlimWithPassword(User user) {
        return userMapper.userToUserToUserSlimWithPassword(user);
    }

    public UserSlimWithOutPassword convertUserToUserSlimWithOutPassword(User user) {
        return userMapper.userToUserToUserSlimWithOutPassword(user);
    }

    public void update(Long userId, String firstName, String lastName, String username) {
        userRepository.updateUserDetails(userId, firstName, lastName, username);
    }

    public User getLoggedUser() {
        return findById(((CurrentUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser()
                .getId());
    }

    public Long getLoggedUserId() {
        return getLoggedUser().getId();
    }

    public Collection<? extends GrantedAuthority> getLoggedUserAuthority() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public boolean isLoggedAsAdmin(){
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_ADMIN));
    }
    public boolean isLoggedAsUser(){
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_USER));
    }
    public boolean isAnonymous(){
        return getLoggedUserAuthority().contains(Factory.create(RoleName.ROLE_ANONYMOUS));
    }
}
