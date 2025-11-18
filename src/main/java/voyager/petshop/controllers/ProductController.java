package voyager.petshop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.Product;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.services.ProductImageSave;
import voyager.petshop.services.ProductValidationService;
import voyager.petshop.services.interfaces.IModelsValidationService;



@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageSave productImageSave;

    private final IModelsValidationService iModelsValidationService = new ProductValidationService();

    private final String uploadDir = "src/main/resources/static/uploads";

    @GetMapping("/all-products")
    public ModelAndView showAllProducts() {
        ModelAndView mv = new ModelAndView("product/all_products");
        List<Product> products = productRepository.findAll();

        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/details/{id}")
    public ModelAndView getMethodName(@PathVariable UUID id) {
        var mv = new ModelAndView("product/details_product");
        var product = new Product();

        if(id != null)
            product = productRepository.findByProductId(id);
        if(product == null)
            product = new Product();

        mv.addObject("product", product);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addProductView(@ModelAttribute("product") Product product,
                                       @ModelAttribute("exception") ModelException exception) {
        ModelAndView mv = new ModelAndView("product/add_product");

        if (product == null)
            product = new Product();
        if (exception == null)
            exception = new ModelException("", new HashMap<>());

        mv.addObject("product", product);
        mv.addObject("exception", exception);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView addProduct(@ModelAttribute("product") Product product,
                                   @RequestParam("Images") List<MultipartFile> Images) {
        ModelAndView mv;
        try {
            if(product != null) {
                iModelsValidationService.modelValidatingEmptyFields(product);
                productRepository.save(product);
                productImageSave.saveImageInDB(Images, product, uploadDir);
                mv = new ModelAndView("redirect:/");
                return mv;
            }

            mv = new ModelAndView("redirect:/product/add");
            return mv;
        }
        catch(ModelException exception) {
            mv = new ModelAndView("product/add_product");
            mv.addObject("exception", exception);
            mv.addObject("product", product);
            return mv;
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
            mv = new ModelAndView("product/add_product");
            product = new Product();

            mv.addObject("product", product);
            return mv;
        }
    }

}
