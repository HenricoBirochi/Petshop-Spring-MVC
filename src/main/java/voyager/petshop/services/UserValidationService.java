package voyager.petshop.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.User;
import voyager.petshop.models.interfaces.IModel;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.services.interfaces.IModelsValidationService;

@Service
public class UserValidationService implements IModelsValidationService {

    private final UserRepository repository;

    public UserValidationService(UserRepository repository) {
        this.repository = repository;
    }

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
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            specificErrors.put("emptyPassword", "Field 'password' is empty");
        }

        if (!specificErrors.isEmpty()) {
            throw new ModelException("There is empty fields", specificErrors);
        }
    }

}
