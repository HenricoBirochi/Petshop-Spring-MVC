package voyager.petshop.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.dtos.LoginForm;
import voyager.petshop.exceptions.ModelException;
import voyager.petshop.exceptions.WrongCredentialsException;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.UserRoles;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.services.SessionLoginService;
import voyager.petshop.services.models.IModelsValidationService;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userValidator")
    private IModelsValidationService iModelsValidationService;

    @Autowired
    private SessionLoginService sessionLoginService;

    @GetMapping("/sign-up")
    public ModelAndView signUpPage(@ModelAttribute("user") User user,
                                   @ModelAttribute("exception") ModelException exception) {
        var mv = new ModelAndView("user/sign_up");

        if (user == null)
            user = new User();
        if (exception == null)
            exception = new ModelException("", new HashMap<>());

        mv.addObject("user", user);
        mv.addObject("exception", exception);
        return mv;
    }

    @PostMapping("/sign-up")
    public ModelAndView signUpPageForm(@ModelAttribute("user") User user) {
        ModelAndView mv;
        try {
            if (user != null) {
                iModelsValidationService.modelValidatingEmptyFields(user);
                iModelsValidationService.modelValidatingIfExists(user);
                user.setUserRole(UserRoles.USER);
                userRepository.save(user);
                mv = new ModelAndView("redirect:/");
                return mv;
            }
            mv = new ModelAndView("redirect:/user/sign-up");
            return mv;
        }
        catch(ModelException exception) {
            mv = new ModelAndView("user/sign_up");
            mv.addObject("user", user);
            mv.addObject("exception", exception);
            return mv;
        }
    }

    @GetMapping("/sign-in")
    public ModelAndView signInPage(@ModelAttribute("loginForm") LoginForm loginForm,
                                   @ModelAttribute("exception") WrongCredentialsException exception) {
        ModelAndView mv = new ModelAndView("user/sign_in");

        if (loginForm == null)
            loginForm = new LoginForm("", "");

        mv.addObject("loginForm", loginForm);
        return mv;
    }

    @PostMapping("/sign-in")
    public ModelAndView signInPageForm(@ModelAttribute("loginForm") LoginForm loginForm,
                                       HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/");

        try {
            mv = sessionLoginService.verifyUserFormCredentials(loginForm, request);
        }
        catch(WrongCredentialsException exception) {
            mv.setViewName("user/sign_in");
            mv.addObject("exception", exception);
            mv.addObject("loginForm", loginForm);
            return mv;
        }
        return mv;
    }

}
