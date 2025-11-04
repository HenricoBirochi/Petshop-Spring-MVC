package voyager.petshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import voyager.petshop.exceptions.UserFieldsException;
import voyager.petshop.models.User;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.utils.UserFieldsVerification;

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
    public ModelAndView signUpPageForm(User user) {
        ModelAndView mv = new ModelAndView();
        try {
            if (user != null) {
                UserFieldsVerification.userValidatingFields(user);
                userRepository.save(user);
                mv = new ModelAndView("redirect:/");
                return mv;
            }
            mv = new ModelAndView("redirect:/user/sing_up");
            return mv;
        }
        catch(UserFieldsException exception) {
            mv = new ModelAndView("redirect:/user/sing_up");
            mv.addObject(user);
            mv.addObject(exception);
            return mv;
        }
    }

    @GetMapping("/sing-in")
    public String getMethodName() {
        return "user/sing_in";
    }

}
