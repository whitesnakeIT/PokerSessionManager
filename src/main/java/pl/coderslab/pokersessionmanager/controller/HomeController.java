package pl.coderslab.pokersessionmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
@GetMapping("/")
//@ResponseBody
    public String showIndex(){
    return "index";
}


//@GetMapping("/error")
//@ResponseBody
//    public String showError(){
//    return "error?";
//}
}
