package voyager.petshop.utils;

import java.util.HashMap;
import java.util.Map;

import voyager.petshop.exceptions.UserFieldsException;
import voyager.petshop.models.User;

public class UserFieldsVerification {

    public static void userValidatingFields(User user) throws UserFieldsException {
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
            throw new UserFieldsException("There is empty fields", specificErrors);
        }
    }

}
