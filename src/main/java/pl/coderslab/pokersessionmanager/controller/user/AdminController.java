package pl.coderslab.pokersessionmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    //do usuniecia zwyklych czy adminow tez

    @GetMapping("/users/all")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userService.findAll());

        return "admin/usersList";
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.delete(userId);

        return "redirect:/admin/users/all";
    }
}