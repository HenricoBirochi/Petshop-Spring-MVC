package voyager.petshop.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shopping_cart_id")
    private UUID shoppingCartId;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.REMOVE)
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
