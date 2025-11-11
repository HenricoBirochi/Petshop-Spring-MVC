package voyager.petshop.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import voyager.petshop.exceptions.ModelException;
import voyager.petshop.models.Product;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.services.ProductValidationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductValidationService productValidationService;

    @GetMapping("/all-products")
    public ModelAndView showAllProducts() {
        ModelAndView mv = new ModelAndView("product/all_products");
        var products = productRepository.findAll();
        mv.addObject("products", products);
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
    public ModelAndView addProduct(@ModelAttribute("product") Product product) {
        ModelAndView mv = new ModelAndView("");
        try{
            if (product != null) {
                productValidationService.modelValidatingEmptyFields(product);
                mv = new ModelAndView("redirect:/");
                productRepository.save(product);
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

}
