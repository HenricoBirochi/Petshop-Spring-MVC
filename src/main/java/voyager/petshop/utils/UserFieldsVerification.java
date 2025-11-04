package voyager.petshop.utils;

import java.util.ArrayList;
import java.util.List;

import voyager.petshop.exceptions.UserFieldsException;
import voyager.petshop.models.User;

public class UserFieldsVerification {

    public static void userValidatingFields(User user) throws UserFieldsException {
        List<String> specificErrors = new ArrayList<>();
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            specificErrors.add("Field 'name' is empty");
        }
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            specificErrors.add("Field 'user name' is empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            specificErrors.add("Field 'email' is empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            specificErrors.add("Field 'password' is empty");
        }
        if (!specificErrors.isEmpty()) {
            throw new UserFieldsException("There is empty fields", specificErrors);
        }
    }

}
