package voyager.petshop.services.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.User;
import voyager.petshop.models.interfaces.IModel;
import voyager.petshop.repositories.UserRepository;

@Service("userValidator")
public class UserValidationService implements IModelsValidationService {

    @Autowired
    private UserRepository repository;

    @Override
    public void modelValidatingIfExists(IModel model) throws ModelException {
        User user = (User)model;

        if (repository.findByUserName(user.getUserName()) != null) {
            throw new ModelException("It already has a user with this user name", new HashMap<>());
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            throw new ModelException("It already has a user with this email" , new HashMap<>());
        }
    }

    @Override
    public void modelValidatingEmptyFields(IModel model) throws ModelException {
        User user = (User)model;

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
        if (!user.getEmail().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            specificErrors.put("wrongPatternEmail", "Field 'email' is in a wrong pattern");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            specificErrors.put("emptyPassword", "Field 'password' is empty");
        }
        if (!user.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$")) {
            specificErrors.put("wrongPatternPassword", "Field 'password' must be at least 8 characters long and contain at least 1 uppercase character, 1 special character, and 1 number.");
        }

        if (!specificErrors.isEmpty()) {
            throw new ModelException("There is something wrong in the form", specificErrors);
        }
    }

}
