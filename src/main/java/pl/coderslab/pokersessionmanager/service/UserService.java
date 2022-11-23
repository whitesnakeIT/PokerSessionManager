package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.UserMapper;
import pl.coderslab.pokersessionmanager.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public void create(User user) {
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

//    public UserBasicInfoWithOutPasswordDto convertUserToUserBasicInfoWithOutPasswordDto(User user) {
//
//        return userMapper.userToUserBasicInfoWithOutPasswordDto(user);
//    }
//
//    public UserBasicInfoWithPasswordDto convertUserToUserBasicInfoWithPasswordDto(User user) {
//
//        return userMapper.userToUserBasicInfoWithPasswordDto(user);
//    }


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
