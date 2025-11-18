package voyager.petshop.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import voyager.petshop.dtos.Cart;
import voyager.petshop.dtos.CartItem;
import voyager.petshop.models.Product;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.services.HandleCartInCookieService;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private HandleCartInCookieService handleCartInCookie;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/view")
    public ModelAndView viewCart(@CookieValue(value = "jsonCookieCart", required = false) String jsonCookieCart,
                                 HttpServletResponse response) {
        var mv = new ModelAndView("cart/view_cart");
        Cart cart;

        try {
            cart = handleCartInCookie.getOrInitCartInCookie(jsonCookieCart, response);
        }
        catch(IOException exception) {
            return mv;
        }

        mv.addObject("cart", cart);
        return mv;
    }

    @PostMapping("/add-item")
    public ModelAndView addItemToCart(@RequestParam("productId") UUID productId,
                                      @RequestParam("quantity") Integer quantity,
                                      @CookieValue(value = "jsonCookieCart", required = false) String jsonCookieCart,
                                      HttpServletResponse response) {
        var mv = new ModelAndView("redirect:/cart/view");

        Product product = productRepository.findByProductId(productId);

        CartItem cartItem = new CartItem(product, quantity);

        try {
            handleCartInCookie.addItemInCartCookie(cartItem, jsonCookieCart, response);
        }
        catch(IOException exception) {
            return mv;
        }

        return mv;
    }

}
