package voyager.petshop.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.dtos.LoginForm;
import voyager.petshop.exceptions.ModelException;
import voyager.petshop.exceptions.WrongCredentialsException;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.UserRoles;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.services.UserValidationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidationService userValidationService;

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
        ModelAndView mv = new ModelAndView();
        try {
            if (user != null) {
                userValidationService.modelValidatingEmptyFields(user);
                userValidationService.modelValidatingIfExists(user);
                // Set the User Role
                // user.setUserRole(UserRoles.Client);
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
        ModelAndView mv = new ModelAndView();
        User user = new User();

        try {

            boolean isEmail = loginForm.getCredential().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");

            if (isEmail) {
                user = userRepository.findByEmail(loginForm.getCredential());
                if (user != null && user.getPassword().equals(loginForm.getPassword())) {
                    var session = request.getSession();
                    session.setAttribute("user", user);

                    mv = new ModelAndView("redirect:/");
                    return mv;
                }
            }
            else {
                user = userRepository.findByUserName(loginForm.getCredential());
                if (user != null && user.getPassword().equals(loginForm.getPassword())) {
                    var session = request.getSession();
                    session.setAttribute("user", user);

                    mv = new ModelAndView("redirect:/");
                    return mv;
                }
            }
            throw new WrongCredentialsException("Credentials are wrong or you don't have an account");
        }
        catch(WrongCredentialsException exception) {
            mv.setViewName("user/sign_in");
            mv.addObject("exception", exception);
            mv.addObject("loginForm", loginForm);
            return mv;
        }
    }

}
