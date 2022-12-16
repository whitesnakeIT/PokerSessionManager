package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
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
        User user =  loggedUser.getUser();

//        UserBasicInfoWithOutPasswordDto userBasicInfo = playerService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.findUserBasicInfoWithOutPasswordDtoById(userId);
        model.addAttribute("userBasicInfo", user);

        return "user/data/showUserDetails";

    }

    @GetMapping("/edit-details")
    public String editUserDetailsGet(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
//        UserBasicInfoWithOutPasswordDto userBasicInfoWithOutPassword = playerService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithPasswordDto userBasicInfoEdit = userService.findUserBasicInfoWithPasswordDto(userId);
        model.addAttribute("userBasicInfoEdit", user);
        return "user/data/editUserDetails";
    }

    @PostMapping("/edit-details")
    public String editUserDetailsPost(@Valid @ModelAttribute(name = "userBasicInfoEdit") UserBasicInfoWithOutPasswordDto userBasicInfoEdit,
                                      BindingResult result,
                                      @AuthenticationPrincipal CurrentUser loggedUser,
                                      @RequestParam String passwordToCheck) {

        User user =  loggedUser.getUser();

        if (userService.isPasswordCorrect(passwordToCheck, user.getPassword())) {

            if (result.hasErrors()) {
                System.out.println("bledy z inputow");
                return "user/data/editUserDetails";
            }
        } else {
            System.out.println("wrong password");
            return "user/data/editUserDetails";
        }
       user.setFirstName(userBasicInfoEdit.getFirstName());
       user.setLastName(userBasicInfoEdit.getLastName());
//        user.setEmail(userBasicInfoEdit.getEmail()); disabled giving null need to be readonly

        // do poprawy !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        userService.create(user);

        return "redirect:/app/user/show-details?msg=data-changed";
    }

    //    ViewController daje 405
    @GetMapping("/edit-password")
    public String editUserPasswordGet() {

        return "user/data/editPassword";
    }

    @PostMapping("/edit-password")
    public String editUserPasswordPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword) {
        User user = loggedUser.getUser();
        if (!userService.updatePassword(user, oldPassword, newPassword, confirmNewPassword)) {
            return "user/data/editPassword";
        }
        return "redirect:/app/user/show-details?msg=password-changed";

    }
}
