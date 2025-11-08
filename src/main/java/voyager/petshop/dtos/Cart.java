package voyager.petshop.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private Long cartId;

    private List<CartItem> cartItems;

    public BigDecimal getTotalPriceCart() {
        BigDecimal totalPriceCart = BigDecimal.ZERO;
        for (int i = 0; i < cartItems.size(); i++) {
            BigDecimal item = cartItems.get(i).getTotalPriceItem();
            if(item == null || item == BigDecimal.ZERO)
                continue;
            totalPriceCart = totalPriceCart.add(item);
        }
        return totalPriceCart;
    }

}
