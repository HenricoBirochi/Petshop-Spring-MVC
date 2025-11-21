package voyager.petshop.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import voyager.petshop.dtos.LoginForm;
import voyager.petshop.exceptions.WrongCredentialsException;
import voyager.petshop.models.User;
import voyager.petshop.repositories.UserRepository;

@Service
public class SessionLoginService {

    @Autowired
    private UserRepository userRepository;

    public ModelAndView verifyUserFormCredentials(LoginForm loginForm, HttpServletRequest request) throws WrongCredentialsException {
        boolean isEmail = loginForm.getCredential().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
        ModelAndView mv;
        User user;

        if (isEmail) {
            user = userRepository.findByEmail(loginForm.getCredential());
        }
        else {
            user = userRepository.findByUserName(loginForm.getCredential());
        }
        if (user != null && user.getPassword().equals(loginForm.getPassword())) {

            setUserInSession(user, request);

            mv = new ModelAndView("redirect:/");
            return mv;
        }
        throw new WrongCredentialsException("Credentials are wrong or you don't have an account");
    }

    public void setUserInSession(User user, HttpServletRequest request) {
        var session = request.getSession();
        session.setAttribute("user", user);
    }

}
