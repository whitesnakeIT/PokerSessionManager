package pl.coderslab.pokersessionmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/")
    @ResponseBody
    public String securityHome(){return "home";}

    @GetMapping("/about")
    @ResponseBody
    public String about() { return "Here you can find some details for logged users"; }
}
