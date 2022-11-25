package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithOutPasswordDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserBasicInfoWithPasswordDto;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/show-details/{userId}")
    public String showUserDetails(@PathVariable Long userId, Model model) {
        UserBasicInfoWithOutPasswordDto userBasicInfo = userService.findUserBasicInfoWithOutPasswordDtoById(userId);
        model.addAttribute("userBasicInfo", userBasicInfo);

        return "/user/showUserDetails";

    }

    @GetMapping("/edit-details/{userId}")
    public String editUserDetailsGet(@PathVariable Long userId, Model model) {

        UserBasicInfoWithPasswordDto userBasicInfoEdit = userService.findUserBasicInfoWithPasswordDto(userId);
        model.addAttribute("userBasicInfoEdit", userBasicInfoEdit);

        return "user/editUserDetails";
    }

    @PostMapping("/edit-details/{userId}")
    public String editUserDetailsPost(@PathVariable Long userId, UserBasicInfoWithPasswordDto userGetDtoById) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userGetDtoById.getFirstName());
            user.setLastName(userGetDtoById.getLastName());
            user.setEmail(userGetDtoById.getEmail());
            userService.create(user);
        }

        return "redirect:/user/show-details/" + userId;
    }

}
