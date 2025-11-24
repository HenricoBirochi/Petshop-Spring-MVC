package voyager.petshop.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import voyager.petshop.authentication.VerifyIfLogged;
import voyager.petshop.dtos.Cart;
import voyager.petshop.dtos.CartItem;
import voyager.petshop.models.Order;
import voyager.petshop.models.OrderItem;
import voyager.petshop.models.Product;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.OrderStatus;
import voyager.petshop.repositories.OrderRepository;
import voyager.petshop.repositories.ProductRepository;
import voyager.petshop.services.HandleCartInCookieService;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private HandleCartInCookieService handleCartInCookie;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

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
        var mv = new ModelAndView("redirect:/cart/view");

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
    public ModelAndView checkoutCart(@CookieValue(value = "jsonCookieCart", required = false) String jsonCookieCart,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        var mv = new ModelAndView("cart/order_success");
        UUID orderId;

        try {
            var session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                mv = new ModelAndView("redirect:/user/sign-in");
                return mv;
            }

            // Get cart
            Cart cart = handleCartInCookie.getOrInitCartInCookie(jsonCookieCart, response);
            cart.setTotalPriceCart();

            if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
                mv = new ModelAndView("redirect:/cart/view");
                return mv;
            }

            // Validate stock availability
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = productRepository.findByProductId(cartItem.getProduct().getProductId());
                if (product.getStockQuantity() < cartItem.getQuantity()) {
                    mv = new ModelAndView("redirect:/cart/view");
                    // Ideally, we would add an error message here
                    return mv;
                }
            }

            // Create order
            Order order = new Order();
            order.setUser(user);
            order.setTotalAmount(cart.getTotalPriceCart());
            order.setStatus(OrderStatus.PENDING);

            // Create order items and update stock
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItems()) {
                // Get fresh product data from database
                Product product = productRepository.findByProductId(cartItem.getProduct().getProductId());

                // Subtract quantity from stock
                product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                productRepository.save(product);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setUnitPrice(product.getPrice());
                orderItem.setTotalPrice(cartItem.getTotalPriceItem());
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);

            // Save order
            orderRepository.save(order);
            orderId = order.getOrderId();

            /* Here is where we would put the sending email logic to the user */
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
