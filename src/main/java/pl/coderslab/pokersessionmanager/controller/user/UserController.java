package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserSlimWithOutPassword;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;
import pl.coderslab.pokersessionmanager.service.PlayerService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/user")
public class UserController {
    private final UserService userService;

    private final PlayerService playerService;

    @GetMapping("/show-details")
    public String showUserDetails(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
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
                                      @AuthenticationPrincipal CurrentUser loggedUser,
                                      @RequestParam String passwordToCheck,
                                      Model model) {

        User user = loggedUser.getUser();
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
        userService.update(user, userSlimWithOutPassword.getFirstName(), userSlimWithOutPassword.getLastName(), userSlimWithOutPassword.getUsername());
//        userService.create(user);

        return "redirect:/app/user/show-details?msg=data-changed";
    }

    @GetMapping("/edit-password")
    public String editUserPasswordGet() {

        return "user/data/editPassword";
    }

    @PostMapping("/edit-password")
    public String editUserPasswordPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                       Model model,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword) {
        User user = loggedUser.getUser();
        String message;
        if (!userService.updatePassword(user, oldPassword, newPassword, confirmNewPassword)) {
            message = "error";
            model.addAttribute("message", message);
            return "user/data/editPassword";
        }
        message = "password-changed";
        return "redirect:/app/user/show-details?msg=password-changed";

    }
}
