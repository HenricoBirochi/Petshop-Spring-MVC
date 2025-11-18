package voyager.petshop.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private List<CartItem> cartItems;

    private BigDecimal totalPriceCart;

    public Cart() {}

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal setAndGetTotalPriceCart() {
        totalPriceCart = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal item = cartItem.getTotalPriceItem();
            if (item == null || item.equals(BigDecimal.ZERO))
                continue;
            totalPriceCart = totalPriceCart.add(item);
        }
        return totalPriceCart;
    }

}
