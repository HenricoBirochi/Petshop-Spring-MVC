package voyager.petshop.exceptions;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFieldsException extends Exception {

    private Map<String, String> specificErrors;

    public UserFieldsException(String message, Map<String, String> specificErrors) {
        super(message);
        this.specificErrors = specificErrors;
    }

}