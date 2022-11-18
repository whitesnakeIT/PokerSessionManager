package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserGetDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserPostDto;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.MapStructMapperImpl;
import pl.coderslab.pokersessionmanager.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MapStructMapperImpl mapStructMapper;

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
//
//    public UserPostDto convertUserToUserPostDto(User user){
//
//        return mapStructMapper.userToUserPostDto(user);
//    }


    public UserPostDto findUserPostDtoById(Long id){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            return mapStructMapper.userToUserPostDto(userOptional.get());

        }

        throw new RuntimeException("I can't find/convert user by user Id." );
    }
    public UserGetDto findUserGetDtoById(Long id){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            return mapStructMapper.userToUserGetDto(userOptional.get());

        }

        throw new RuntimeException("I can't find/convert user by user Id." );
    }
}
