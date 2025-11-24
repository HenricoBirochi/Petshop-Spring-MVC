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

    private BigDecimal totalPriceItem;

    public CartItem() {}

    public CartItem(UUID cartItemId, Product product, Integer quantity) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
    }

    public void setTotalPriceItem() {
        totalPriceItem = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
