package voyager.petshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/show")
    public String showAllProducts() {
        return new String();
    }

}
