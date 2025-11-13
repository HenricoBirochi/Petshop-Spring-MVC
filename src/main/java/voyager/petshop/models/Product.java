package voyager.petshop.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import voyager.petshop.models.interfaces.IModel;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 300, nullable = true)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

}
