package voyager.petshop.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import voyager.petshop.models.Product;
import voyager.petshop.models.ProductImage;
import voyager.petshop.repositories.ProductImageRepository;

@Service
public class ProductImageSaveService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public void saveImageInDB(List<MultipartFile> productImages, Product product, String uploadDir) throws Exception {
        for (MultipartFile image : productImages) {
            String originalFileName = image.getOriginalFilename();
            if (originalFileName == null)
                throw new Exception("originalFileName variable is empty or null");
            String imageExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            UUID newImageName = UUID.randomUUID();
            Path path = Paths.get(uploadDir + "/" + newImageName.toString() + imageExtension);

            Files.copy(image.getInputStream(), path);

            ProductImage productImage = new ProductImage();
            productImage.setProductImageId(newImageName);
            productImage.setFileName(originalFileName);
            productImage.setFileExtension(imageExtension);
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
    }

}
