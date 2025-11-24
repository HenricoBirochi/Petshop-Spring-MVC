package voyager.petshop.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import voyager.petshop.authentication.VerifyIfLogged;
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
        catch(Exception exception) {
            mv = new ModelAndView("error/error");
            mv.addObject("error", exception.getMessage());
            return mv;
        }

        cart.setTotalPriceCart();
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
        UUID cartItemId = UUID.randomUUID();
        CartItem cartItem = new CartItem(cartItemId, product, quantity);

        try {
            handleCartInCookie.addItemInCartCookie(jsonCookieCart, cartItem, response);
        }
        catch(Exception exception) {
            mv = new ModelAndView("error/error");
            mv.addObject("error", exception.getMessage());
            return mv;
        }

        return mv;
    }

    @PostMapping("/remove-item")
    public ModelAndView removeItemFromCart(@RequestParam("cartItemId") UUID cartItemId,
                                           @CookieValue(value = "jsonCookieCart", required = false) String jsonCookieCart,
                                           HttpServletResponse response) {
        var mv = new ModelAndView("redirect:/");

        try {
            handleCartInCookie.removeItemFromCartCookie(jsonCookieCart, cartItemId, response);
        }
        catch (Exception exception) {
            mv = new ModelAndView("error/error");
            mv.addObject("error", exception.getMessage());
            return mv;
        }

        return mv;
    }

    @VerifyIfLogged
    @GetMapping("/checkout")
    public ModelAndView checkoutCart(HttpServletResponse response) {
        var mv = new ModelAndView("cart/order_success");
        UUID orderId;

        try {
            orderId = UUID.randomUUID();
            /* Hear is where we would put the sending email logic to the user */
            handleCartInCookie.clearCartCookie(response);
        }
        catch (Exception exception) {
            mv = new ModelAndView("error/error");
            mv.addObject("error", exception.getMessage());
            return mv;
        }

        mv.addObject("orderId", orderId);
        return mv;
    }

}
