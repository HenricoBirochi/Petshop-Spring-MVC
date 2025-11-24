package voyager.petshop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.authentication.VerifyIfIsAdmin;
import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.Product;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.services.ProductImageSaveService;
import voyager.petshop.services.models.IModelsValidationService;
import voyager.petshop.models.ProductImage;
import voyager.petshop.repositories.ProductImageRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;



@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageSaveService productImageSaveService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    @Qualifier("productValidator")
    private IModelsValidationService iModelsValidationService;

    private final String uploadDir = "src/main/resources/static/uploads";

    @GetMapping("/all-products")
    public ModelAndView showAllProducts() {
        ModelAndView mv = new ModelAndView("product/all_products");
        List<Product> products = productRepository.findAll();

        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/details/{id}")
    public ModelAndView showDetailsProduct(@PathVariable UUID id) {
        var mv = new ModelAndView("product/details_product");
        var product = new Product();

        if(id != null)
            product = productRepository.findByProductId(id);
        if(product == null)
            product = new Product();

        mv.addObject("product", product);
        return mv;
    }

    @VerifyIfIsAdmin
    @GetMapping("/add")
    public ModelAndView addProductView(@ModelAttribute("product") Product product,
                                       @ModelAttribute("exception") ModelException exception,
                                       HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("product/add_product");

        if (product == null)
            product = new Product();
        if (exception == null)
            exception = new ModelException("", new HashMap<>());

        mv.addObject("product", product);
        mv.addObject("exception", exception);
        return mv;
    }

    @VerifyIfIsAdmin
    @PostMapping("/add")
    public ModelAndView addProduct(@ModelAttribute("product") Product product,
                                   @RequestParam("images") List<MultipartFile> images,
                                   HttpServletRequest request) throws Exception {
        ModelAndView mv;
        try {
            if(product != null) {
                iModelsValidationService.modelValidatingEmptyFields(product);
                productRepository.save(product);
                productImageSaveService.saveImageInDB(images, product, uploadDir);
                mv = new ModelAndView("redirect:/");
                return mv;
            }

            mv = new ModelAndView("redirect:/product/add");
            return mv;
        }
        catch (ModelException exception) {
            mv = new ModelAndView("product/add_product");
            mv.addObject("exception", exception);
            mv.addObject("product", product);
            return mv;
        }
    }

    @VerifyIfIsAdmin
    @PostMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable UUID id) {
        ModelAndView mv = new ModelAndView("redirect:/product/all-products");
        try {
            var product = productRepository.findByProductId(id);
            if (product != null) {
                // take a copy of images (avoid potential lazy/managed collection issues)
                var images = product.getProductImages() != null ? new ArrayList<>(product.getProductImages()) : Collections.<ProductImage>emptyList();

                // delete files from disk first
                for (ProductImage img : images) {
                    Path p = Paths.get(uploadDir + "/" + img.getProductImageId().toString() + img.getFileExtension());
                    try {
                        Files.deleteIfExists(p);
                    } catch (Exception ignored) {}
                }

                // delete image records from DB
                if (!images.isEmpty()) {
                    var ids = images.stream().map(ProductImage::getProductImageId).collect(Collectors.toList());
                    productImageRepository.deleteAllById(ids);
                    try { productImageRepository.flush(); } catch (Exception ignored) {}
                }

                // finally delete the product
                try { productRepository.deleteById(id); } catch (Exception e) { productRepository.delete(product); }
            }
        } catch (Exception ignored) {}
        return mv;
    }

}
