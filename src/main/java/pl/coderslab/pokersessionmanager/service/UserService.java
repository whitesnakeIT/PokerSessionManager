package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
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

    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1); // maybe default ??
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(List.of(userRole)));
        userRepository.save(user);
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

        Optional<User> byEmail = userRepository.findByEmail(email);
        System.out.println(byEmail);
        return byEmail;

//        return userRepository.findByEmail(email);
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
}
