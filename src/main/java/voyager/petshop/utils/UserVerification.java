package voyager.petshop.utils;

import voyager.petshop.models.User;

public class UserVerification {

    public static String userVerifyNullFields(User user) {
        if (user.getName() == "" || user.getUserName() == "" 
        || user.getEmail() == "" || user.getPassword() == "") {
            return "A field is empty";
        }
        return "There is no empty field";
    }

}
