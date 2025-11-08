package voyager.petshop.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import voyager.petshop.models.Product;

@Getter
@Setter
public class CartItem {

    private UUID cartItemId;

    private Product product;

    private Integer quantity;

    private Cart cart;

    public BigDecimal getTotalPriceItem() {
        BigDecimal totalPriceItem = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        return totalPriceItem;
    }

}
