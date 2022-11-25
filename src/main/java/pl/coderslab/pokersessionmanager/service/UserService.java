package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Role;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.UserMapper;
import pl.coderslab.pokersessionmanager.repository.RoleRepository;
import pl.coderslab.pokersessionmanager.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;

    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isPasswordCorrect(String passwordToCheck, String userPassword) {

        return bCryptPasswordEncoder.matches(passwordToCheck, userPassword);
    }

    public void create(User user) {
// if user exist then don't need to encode his password again -> details editing
        if (findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(1); // maybe default ??
            Role userRole = roleRepository.findByName("ROLE_USER");
            user.setRoles(new HashSet<>(List.of(userRole)));
//            if password is not updated, it is not encoding second time
        } else if (user.getPassword()
                .equals(findByEmail(user.getEmail()).get().getPassword())) {
            userRepository.save(user);
            // if password is updated it have to be encoded

        }

        userRepository.save(user);
    }

    // update ??
    public boolean updatePassword(User user, String oldPassword, String newPassword, String confirmNewPassword) {

        boolean compareActualAndOldPassword = bCryptPasswordEncoder.matches(oldPassword,user.getPassword());


        boolean compareActualAndNewPassword = bCryptPasswordEncoder.matches(newPassword,user.getPassword());
        boolean compareActualAndConfirmNewPassword = bCryptPasswordEncoder.matches(confirmNewPassword,user.getPassword());


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


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Optional<User> findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    public Optional<User> findByEmail(String email) {

//        Optional<User> byEmail = userRepository.findByEmail(email);
//        System.out.println(byEmail);
//        return byEmail;
// zmienic na zwyklego usera
        return userRepository.findByEmail(email);
    }

    public UserBasicInfoWithPasswordDto findUserBasicInfoWithPasswordDto(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            return userMapper.userToUserBasicInfoWithPasswordDto(userOptional.get());

        }

        throw new RuntimeException("I can't find/convert user by user Id.");
    }

    public UserBasicInfoWithOutPasswordDto findUserBasicInfoWithOutPasswordDtoById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            return userMapper.userToUserBasicInfoWithOutPasswordDto(userOptional.get());

        }

        throw new RuntimeException("I can't find/convert user by user Id.");
    }

    public UserBasicInfoWithPasswordDto convertUserToUserBasicInfoWithPasswordDto(User user) {
        return userMapper.userToUserBasicInfoWithPasswordDto(user);
    }

    public UserBasicInfoWithOutPasswordDto convertUserToUserBasicInfoWithOutPasswordDto(User user) {
        return userMapper.userToUserBasicInfoWithOutPasswordDto(user);
    }

    public void loadFavouriteTournamentsToUser(User user) {
        Hibernate.initialize(user.getFavouriteTournaments());
    }

    public void loadSessionsToUser(User user) {
        Hibernate.initialize(user.getSessions());
    }

    //  moze lepiej w serwisie ?
//    public User loadLoggedUser(@AuthenticationPrincipal CurrentUser loggedUser)
//    {
//        return loggedUser.getUser();
//
//    }
}
