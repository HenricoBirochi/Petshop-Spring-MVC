package voyager.petshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.dtos.LoginForm;
import voyager.petshop.exceptions.WrongCredentialsException;
import voyager.petshop.models.User;
import voyager.petshop.repositories.UserRepository;

@Service
public class SessionLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingOfUserPasswordService hashingOfUserPasswordService;

    public User verifyUserFormCredentials(LoginForm loginForm, HttpServletRequest request) throws WrongCredentialsException {
        boolean isEmail = loginForm.getCredential().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
        User user;

        if (isEmail) {
            user = userRepository.findByEmail(loginForm.getCredential());
        }
        else {
            user = userRepository.findByUserName(loginForm.getCredential());
        }
        if (user == null)
            throw new WrongCredentialsException("Credentials are wrong or you don't have an account");

        if (hashingOfUserPasswordService.checkPassword(loginForm.getPassword(), user.getPassword()))
            return user;

        throw new WrongCredentialsException("Credentials are wrong or you don't have an account");
    }

    public void setUserInSession(User user, HttpServletRequest request) {
        var session = request.getSession();
        session.setAttribute("user", user);
    }

    public void clearUserSession(HttpServletRequest request) {
        var session = request.getSession();
        session.setAttribute("user", null);
    }

}
