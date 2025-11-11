package voyager.petshop.services.interfaces;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.interfaces.IModel;

public interface IModelsValidationService {

    public void modelValidatingIfExists(IModel model) throws ModelException;
    public void modelValidatingEmptyFields(IModel model) throws ModelException;

}
