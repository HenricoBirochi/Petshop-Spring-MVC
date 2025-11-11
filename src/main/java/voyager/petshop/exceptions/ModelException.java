package voyager.petshop.exceptions;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelException extends Exception {

    private Map<String, String> specificErrors;

    public ModelException(String message, Map<String, String> specificErrors) {
        super(message);
        this.specificErrors = (specificErrors != null) ? specificErrors : new HashMap<>();
    }

}