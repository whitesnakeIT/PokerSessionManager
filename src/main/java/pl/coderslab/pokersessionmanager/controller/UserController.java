package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class UserController {

//    private final TournamentService tournamentService;

    private final UserService userService;

//    @GetMapping("/dashboard")
//    public String showDashboard() {
//        return "user/dashboard";
//    }


    @GetMapping("/user/show-details")
    public String showUserDetails(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.findUserBasicInfoWithOutPasswordDtoById(userId);
        model.addAttribute("userBasicInfo", userBasicInfo);

        return "user/data/showUserDetails";

    }

    @GetMapping("/user/edit-details")
    public String editUserDetailsGet(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
        UserBasicInfoWithOutPasswordDto userBasicInfoWithOutPassword = userService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithPasswordDto userBasicInfoEdit = userService.findUserBasicInfoWithPasswordDto(userId);
        model.addAttribute("userBasicInfoEdit", userBasicInfoWithOutPassword);
        return "user/data/editUserDetails";
    }

    @PostMapping("/user/edit-details")
    public String editUserDetailsPost(@Valid @ModelAttribute(name = "userBasicInfoEdit") UserBasicInfoWithOutPasswordDto userBasicInfoEdit,
                                      BindingResult result,
                                      @AuthenticationPrincipal CurrentUser loggedUser,
                                      @RequestParam String passwordToCheck) {

        User user = loggedUser.getUser();

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
        userService.create(user);

        return "redirect:/app/user/show-details?msg=data-changed";
    }

    //    ViewController daje 405
    @GetMapping("/user/edit-password")
    public String editUserPasswordGet() {

        return "user/data/editPassword";
    }

    @PostMapping("/user/edit-password")
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

//    @ModelAttribute("availableTournamentTypes")
//    public List<String> getAvailableTournamentTypes() {
//        return tournamentService.getAvailableTournamentTypes();
//    }
//
//    @ModelAttribute("availableTournamentSpeed")
//    public List<String> getAvailableTournamentSpeed() {
//        return tournamentService.getAvailableTournamentSpeed();
//    }
}



