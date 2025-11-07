package voyager.petshop.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import voyager.petshop.exceptions.UserException;
import voyager.petshop.models.User;
import voyager.petshop.repositories.UserRepository;

@Component
public class UserValidation {

    private final UserRepository repository;

    public UserValidation(UserRepository repository) {
        this.repository = repository;
    }

    public void userValidatingIfExists(User user) throws UserException {
        if (repository.findByUserName(user.getUserName()) != null) {
            throw new UserException("It already has a user with this user name", new HashMap<>());
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            throw new UserException("It already has a user with this email" , new HashMap<>());
        }
    }

    public void userValidatingEmptyFields(User user) throws UserException {
        Map<String, String> specificErrors = new HashMap<>();

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            specificErrors.put("emptyName", "Field 'name' is empty");
        }
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            specificErrors.put("emptyUserName" ,"Field 'user name' is empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            specificErrors.put("emptyEmail", "Field 'email' is empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            specificErrors.put("emptyPassword", "Field 'password' is empty");
        }

        if (!specificErrors.isEmpty()) {
            throw new UserException("There is empty fields", specificErrors);
        }
    }

}
