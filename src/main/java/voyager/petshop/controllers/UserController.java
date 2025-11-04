package voyager.petshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import voyager.petshop.models.User;
import voyager.petshop.repositories.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sing-up")
    public String signUpPage() {
        return "user/sing_up";
    }

    @PostMapping("/sing-up")
    public String signUpPageForm(User user) {
        if (user != null)
            userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/sing-in")
    public String getMethodName() {
        return "user/sing_in";
    }

}
