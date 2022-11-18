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
import pl.coderslab.pokersessionmanager.mapstruct.dto.user.UserGetDto;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final TournamentService tournamentService;

    private final UserService userService;


//    @GetMapping("/add")
//    public String addNewTournamentGet(Model model) {
//        model.addAttribute(new Tournament());
//
//        return "tournament/form";
//    }
//
//    @PostMapping("/add")
//    public String addNewTournamentGet(@Valid Tournament tournament, BindingResult result) {
//        if (result.hasErrors()) {
//            return "tournament/form";
//        }
//        tournamentService.create(tournament);
//        return "redirect:/tournament/all";
//    }

    @GetMapping("/favourite/{userId}")
    public String getFavouriteTournaments(Model model, @PathVariable Long userId) {
        List<TournamentSlimDto> tournamentSlimDtoList = tournamentService.findFavouriteTournaments(userId);
        model.addAttribute(tournamentSlimDtoList);
        return "user/favouriteTournaments";
    }

    @GetMapping("/show-details/{userId}")
    public String showUserDetails(@PathVariable Long userId, Model model) {
        UserGetDto userGetDto = userService.findUserGetDtoById(userId);
        model.addAttribute(userGetDto);

        return "/user/showUserDetails";

    }

    @GetMapping("/edit-details/{userId}")
    public String editUserDetailsGet(@PathVariable Long userId, Model model) {

        UserGetDto userGetDtoById = userService.findUserGetDtoById(userId);
        model.addAttribute("userGetDtoById", userGetDtoById);

        return "user/editUserDetails";
    }

    @PostMapping("/edit-details/{userId}")
    public String editUserDetailsPost(@PathVariable Long userId, UserGetDto userGetDtoById) {
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
