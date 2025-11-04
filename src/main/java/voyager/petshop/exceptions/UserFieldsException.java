package voyager.petshop.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFieldsException extends Exception {

    private List<String> specificErrors;

    public UserFieldsException(String message, List<String> specificErrors) {
        super(message);
        this.specificErrors = specificErrors;
    }

}