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

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "user/sign_up";
    }

    @PostMapping("/sign-up")
    public ModelAndView signUpPageForm(User user) {
        ModelAndView mv = new ModelAndView();
        try {
            if (user != null) {
                UserFieldsVerification.userValidatingFields(user);
                userRepository.save(user);
                mv = new ModelAndView("redirect:/");
                return mv;
            }
            mv = new ModelAndView("redirect:/user/sign_up");
            return mv;
        }
        catch(UserFieldsException exception) {
            mv = new ModelAndView("redirect:/user/sign_up");
            mv.addObject(user);
            mv.addObject(exception);
            return mv;
        }
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "user/sign_in";
    }

    @PostMapping("/sign-in")
    public String signInPageForm(String credential, String password) {
        boolean isEmail = credential.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
        User user;
        if (isEmail)
            user = userRepository.findByEmail(credential);
        else
            user = userRepository.findByUserName(credential);
        user.setPassword(password);
        return "redirect:/";
    }
    

}
