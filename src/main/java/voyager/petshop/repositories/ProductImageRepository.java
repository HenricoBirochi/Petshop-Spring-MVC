package voyager.petshop.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import voyager.petshop.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    
}
