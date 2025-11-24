package voyager.petshop.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import voyager.petshop.models.OrderItem;
import voyager.petshop.models.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    boolean existsByProduct(Product product);
    long countByProduct(Product product);
}
