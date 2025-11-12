package voyager.petshop.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import voyager.petshop.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByProductId(UUID productId);
}
