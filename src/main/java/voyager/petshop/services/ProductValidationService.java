package voyager.petshop.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.Product;
import voyager.petshop.models.interfaces.IModel;
import voyager.petshop.services.interfaces.IModelsValidationService;

@Service
public class ProductValidationService implements IModelsValidationService {

    @Override
    public void modelValidatingIfExists(IModel model) throws ModelException {} // Not using for Products

    @Override
    public void modelValidatingEmptyFields(IModel model) throws ModelException {
        Product product = (Product)model;

        Map<String, String> specificErrors = new HashMap<>();

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            specificErrors.put("emptyName", "Field 'product name' is empty");
        }
        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            specificErrors.put("emptyDescription" ,"Field 'product description' is empty");
        }
        if (product.getPrice() == null || product.getPrice().equals(BigDecimal.ZERO)) {
            specificErrors.put("emptyPrice", "Field 'product price' is empty or equals to zero");
        }
        if (product.getStockQuantity() == null) {
            specificErrors.put("emptyStockQuantity", "Field 'product stock quantity' is empty");
        }

        if (!specificErrors.isEmpty()) {
            throw new ModelException("There is empty fields", specificErrors);
        }
    }


}
