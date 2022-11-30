package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
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

    @GetMapping("/tournaments/favourite/add/{tournamentPossibleToFavourites}")
    public String addTournamentToFavouritesPost(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentPossibleToFavourites) {
        User user = loggedUser.getUser();
        tournamentService.addTournamentToFavourites(user.getId(), tournamentPossibleToFavourites);
        return "redirect:/app/tournaments/favourite";
    }


    @GetMapping("/tournaments/favourite/delete/{tournamentId}")
    public String deleteTournamentFromFavouritesGet(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentId) {
        User user = loggedUser.getUser();
        tournamentService.deleteTournamentFromFavourites(user.getId(), tournamentId);

        return "redirect:/app/tournaments/favourite";
    }


    @GetMapping("/tournaments/favourite")
    public String getFavouriteTournaments(Model model, @AuthenticationPrincipal CurrentUser loggedUser) {
        User user = loggedUser.getUser();

        List<TournamentSlimDto> favouriteTournaments =
                tournamentService.convertTournamentToSlimDto(
                        tournamentService.
                                findFavouriteTournaments(user.getId()));
        List<TournamentSlimDto> tournamentsPossibleToFavourites =
                tournamentService.convertTournamentToSlimDto(
                        tournamentService.getListOfTournamentsPossibleToBeFavourites(user.getId()));
        model.addAttribute("favouriteTournaments", favouriteTournaments);
        model.addAttribute("tournamentsPossibleToFavourites", tournamentsPossibleToFavourites);
        return "user/tournament/favouriteTournamentList";
    }

    @GetMapping("/tournaments/suggest/add")
    public String addTournamentsToSuggestions(Model model) {
        model.addAttribute("tournament", new TournamentSuggestion());
        return "tournament/form";
    }

    @PostMapping("/tournaments/suggest/add")
    public String addTournamentToSuggestions(@AuthenticationPrincipal CurrentUser loggedUser,
                                             @Valid @ModelAttribute(name = "tournament") TournamentSuggestion tournamentSuggestion,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return "tournament/form";
        }
        User user = loggedUser.getUser();
        tournamentService.addTournamentToSuggestions(tournamentSuggestion, user.getId());


        return "redirect:/app/tournaments/suggest/all";
    }

    @GetMapping("/tournaments/suggest/all")
    public String getSuggestTournaments(@AuthenticationPrincipal CurrentUser loggedUser, Model model) {
        User user = loggedUser.getUser();
        List<TournamentSuggestion> suggestionTournamentList = tournamentService.findSuggestTournaments(user.getId());
        model.addAttribute("suggestionTournamentList", suggestionTournamentList);
        return "user/tournament/suggestionTournamentList";

    }

    @GetMapping("/tournaments/suggest/delete/{tournamentId}")
    public String deleteTournamentFromSuggestions(@AuthenticationPrincipal CurrentUser loggedUser, @PathVariable Long tournamentId) {
        User user = loggedUser.getUser();

        tournamentService.deleteTournamentFromSuggestion(user.getId(), tournamentId);
        return "redirect:/app/tournaments/suggest/all";
    }

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

    @ModelAttribute("availableTournamentTypes")
    public List<String> getAvailableTournamentTypes() {
        return tournamentService.getAvailableTournamentTypes();
    }

    @ModelAttribute("availableTournamentSpeed")
    public List<String> getAvailableTournamentSpeed() {
        return tournamentService.getAvailableTournamentSpeed();
    }
}



