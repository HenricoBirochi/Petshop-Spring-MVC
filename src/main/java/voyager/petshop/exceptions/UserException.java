package voyager.petshop.exceptions;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends Exception {

    private Map<String, String> specificErrors;

    public UserException(String message, Map<String, String> specificErrors) {
        super(message);
        this.specificErrors = (specificErrors != null) ? specificErrors : new HashMap<>();
    }

}