package voyager.petshop.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import voyager.petshop.models.Product;

@Getter
@Setter
public class CartItem {

    private Product product;

    private Integer quantity;

    private BigDecimal totalPriceItem;

    public CartItem() {}

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setTotalPriceItem() {
        totalPriceItem = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
