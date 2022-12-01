package pl.coderslab.pokersessionmanager.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.service.UserService;


public class UserConverter implements Converter<String,User> {
    @Autowired
    UserService userService;

    @Override
    public User convert(String source) {


        return userService.findById(Long.valueOf(source));
    }
}
