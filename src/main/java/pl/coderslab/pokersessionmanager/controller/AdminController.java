package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.service.TournamentService;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TournamentService tournamentService;

    @GetMapping("/users/all")
    public String showAllUsers(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute(userList);
        return "/admin/allUsersList";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        User user = userService.findById(userId);
        userService.delete(user);
        return "redirect:/admin/all";
    }
}