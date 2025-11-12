package voyager.petshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "main/home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return new String("main/about");
    }
    

}
