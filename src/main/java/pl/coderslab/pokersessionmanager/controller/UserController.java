package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.model.CurrentUser;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class UserController {

    private final TournamentService tournamentService;

    private final UserService userService;

//    @GetMapping("/dashboard")
//    public String showDashboard() {
//        return "user/dashboard";
//    }

    @GetMapping("/favourite/{userId}/{tournamentPossibleToFavourites}/add")
    public String addNewTournamentToFavouritesPost(@PathVariable Long userId, @PathVariable Long tournamentPossibleToFavourites) {
//        System.out.println(httpServletRequest.getParameterMap().entrySet());
//        System.out.println(httpServletRequest.getParameterNames());
//        httpServletRequest.getParameterValues();
        tournamentService.addTournamentToFavourites(userId, tournamentPossibleToFavourites);
        return "redirect:/user/favourite/" + userId;
    }


    @GetMapping("/favourite/{userId}/{tournamentId}/delete")
    public String deleteTournamentFromFavouritesGet(@PathVariable Long userId, @PathVariable Long tournamentId) {
        tournamentService.deleteTournamentFromFavourites(userId, tournamentId);

        return "redirect:/user/favourite/" + userId;
    }


    @GetMapping("/favourite/{userId}")
    public String getFavouriteTournaments(Model model, @PathVariable Long userId) {
        List<TournamentSlimDto> favouriteTournaments = tournamentService.findFavouriteTournaments(userId);
        List<TournamentSlimDto> tournamentsPossibleToFavourites = tournamentService.getListOfTournamentsPossibleToBeFavourites(userId);
        model.addAttribute("favouriteTournaments", favouriteTournaments);
        model.addAttribute("tournamentsPossibleToFavourites", tournamentsPossibleToFavourites);
        return "/user/favouriteTournamentsList";


    }

    @GetMapping("/user/show-details")
    public String showUserDetails(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.findUserBasicInfoWithOutPasswordDtoById(userId);
        model.addAttribute("userBasicInfo", userBasicInfo);

        return "/user/showUserDetails";

    }

    @GetMapping("/user/edit-details")
    public String editUserDetailsGet(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();
        UserBasicInfoWithOutPasswordDto userBasicInfoWithOutPassword = userService.convertUserToUserBasicInfoWithOutPasswordDto(user);
//        UserBasicInfoWithPasswordDto userBasicInfoEdit = userService.findUserBasicInfoWithPasswordDto(userId);
        model.addAttribute("userBasicInfoEdit", userBasicInfoWithOutPassword);
        return "user/editUserDetails";
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
                return "user/editUserDetails";
            }
        } else {
            System.out.println("wrong password");
            return "user/editUserDetails";
        }
        user.setFirstName(userBasicInfoEdit.getFirstName());
        user.setLastName(userBasicInfoEdit.getLastName());
//        user.setEmail(userBasicInfoEdit.getEmail()); disabled brother
        userService.create(user);

        return "redirect:/app/user/show-details?msg=data-changed";
    }

    //    ViewController daje 405
    @GetMapping("/user/edit-password")
    public String editUserPasswordGet() {

        return "user/editPassword";
    }

    @PostMapping("/user/edit-password")
    public String editUserPasswordPost(@AuthenticationPrincipal CurrentUser loggedUser,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword) {

        User user = loggedUser.getUser();
        if (!userService.updatePassword(user, oldPassword, newPassword, confirmNewPassword)) {
            return "user/editPassword";
        }


        return "redirect:/app/user/show-details?msg=password-changed";

    }


}



