package voyager.petshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import voyager.petshop.models.Product;
import voyager.petshop.models.ProductImage;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.UserRoles;
import voyager.petshop.repositories.ProductImageRepository;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.repositories.UserRepository;
import voyager.petshop.services.HashingOfUserPasswordService;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingOfUserPasswordService hashingService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    private final String seedImagesDir = "seed/images";
    private final String uploadDir = "src/main/resources/static/uploads";

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
        seedProducts();
    }

    private void seedAdmin() {
        if (userRepository.findByUserName("admin") == null) {
            User user = new User();
            user.setName("Administrator");
            user.setUserName("admin");
            user.setEmail("admin@local");
            user.setPassword(hashingService.hashPassword("admin"));
            user.setUserRole(UserRoles.ADMIN);
            userRepository.save(user);
            System.out.println("✓ Seeded admin user (admin/admin)");
        }
    }

    private void seedProducts() throws Exception {
        if (productRepository.count() > 0) {
            System.out.println("✓ Products already seeded, skipping...");
            return;
        }

        // Ensure upload directory exists
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Seed products with images from seed/images
        seedProduct("Premium Dog Food", 
                   "High-quality nutritious dog food with real chicken and vegetables. Perfect for adult dogs of all breeds.",
                   new BigDecimal("45.99"), 100L, "dog-food.webp");

        seedProduct("Gourmet Cat Food", 
                   "Delicious cat food made with fresh salmon and tuna. Specially formulated for cats' nutritional needs.",
                   new BigDecimal("38.50"), 150L, "cat-food.png");

        seedProduct("Cozy Pet Bed", 
                   "Ultra-soft and comfortable pet bed with memory foam support. Machine washable cover included.",
                   new BigDecimal("79.99"), 45L, "pets-bed.webp");

        seedProduct("Stainless Steel Pet Bowl", 
                   "Durable stainless steel bowl set with non-slip rubber base. Dishwasher safe and rust-resistant.",
                   new BigDecimal("24.99"), 200L, "pets-bowl.webp");

        System.out.println("✓ Seeded 4 products with images");
    }

    private void seedProduct(String name, String description, BigDecimal price, Long stock, String imageName) throws Exception {
        // Create product
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stock);
        productRepository.save(product);

        // Copy image from seed/images to uploads
        Path sourcePath = Paths.get(seedImagesDir, imageName);
        if (Files.exists(sourcePath)) {
            String extension = imageName.substring(imageName.lastIndexOf("."));
            UUID imageId = UUID.randomUUID();
            Path targetPath = Paths.get(uploadDir, imageId.toString() + extension);
            
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Create product image record
            ProductImage productImage = new ProductImage();
            productImage.setProductImageId(imageId);
            productImage.setFileName(imageName);
            productImage.setFileExtension(extension);
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
    }

}
