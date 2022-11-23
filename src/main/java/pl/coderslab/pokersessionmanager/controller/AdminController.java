package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.pokersessionmanager.entity.User;
import pl.coderslab.pokersessionmanager.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute(userList);
        return "/admin/allUsersList";
    }

    @GetMapping("/delete/user/{userId}")
    public String deleteUser(@PathVariable Long userId){
        Optional<User> userOptional = userService.findById(userId);
        userOptional.ifPresent(userService::delete);
        return "redirect:/admin/all";
    }


}
