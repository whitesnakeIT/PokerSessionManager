package pl.coderslab.pokersessionmanager.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PasswordErrors;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithOutPassword;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.UserService;

//import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/show-details")
    public String showUserDetails(Model model) {
        User user = userService.getLoggedUser();
        UserSlimWithOutPassword userSlim = userService.convertUserToUserSlimWithOutPassword(user);
        model.addAttribute("userSlim", userSlim);

        return "user/data/showUserDetails";

    }

    @GetMapping("/edit-details")
    public String editUserDetailsGet(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
        UserSlimWithOutPassword userSlim = userService.convertUserToUserSlimWithOutPassword(user);
        model.addAttribute("userSlim", userSlim);

        return "user/data/editUserDetails";
    }

    @PostMapping("/edit-details")
    public String editUserDetailsPost(@Valid @ModelAttribute(name = "userSlim") UserSlimWithOutPassword userSlimWithOutPassword,
                                      BindingResult result,
                                      @RequestParam String passwordToCheck,
                                      Model model) {

        User user = userService.getLoggedUser();
        boolean wrongPassword = false;
        if (userService.isPasswordCorrect(passwordToCheck, user.getPassword())) {

            if (result.hasErrors()) {
                return "user/data/editUserDetails";
            }
        } else {
            wrongPassword = true;
            model.addAttribute("wrongPassword", wrongPassword);
            return "user/data/editUserDetails";
        }
        user.setFirstName(userSlimWithOutPassword.getFirstName());
        user.setLastName(userSlimWithOutPassword.getLastName());
        user.setUsername(userSlimWithOutPassword.getUsername());
        userService.update(user.getId(), userSlimWithOutPassword.getFirstName(), userSlimWithOutPassword.getLastName(), userSlimWithOutPassword.getUsername());
//        userService.create(user);

        return "redirect:/app/user/show-details?msg=data-changed";
    }

    @GetMapping("/edit-password")
    public String editUserPasswordGet() {

        return "user/data/editPassword";
    }

    @PostMapping("/edit-password")
    public String editUserPasswordPost(Model model,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword) {
        User user = userService.getLoggedUser();
        PasswordErrors message ;
        switch (userService.updatePassword(user, oldPassword, newPassword, confirmNewPassword)) {

            case NO_ERROR -> {
                message = PasswordErrors.NO_ERROR;
            }
            case PASSWORDS_NOT_MATCH -> {
                message = PasswordErrors.PASSWORDS_NOT_MATCH;
            }
            case OLD_PASSWORD_WRONG -> {
                message = PasswordErrors.OLD_PASSWORD_WRONG;
            }
            case EMPTY_INPUT -> {
                message = PasswordErrors.EMPTY_INPUT;
            }
            case NEW_PASSWORD_SAME_AS_OLD -> {
                message = PasswordErrors.NEW_PASSWORD_SAME_AS_OLD;
            }
            default -> {
                throw new RuntimeException("Unable to change password.");
            }

        }
        model.addAttribute("message", message);
        if (!message.equals(PasswordErrors.NO_ERROR)) {
            return "user/data/editPassword";
        } else {
            return "redirect:/app/user/show-details?message="
                    .concat(message.name().toLowerCase());
        }
//        if (!userService.updatePassword(user, oldPassword, newPassword, confirmNewPassword)) {
//            message = "error";
//            model.addAttribute("message", message);
//            return "user/data/editPassword";
//        }
//        message = "password-changed";


    }
}
