package voyager.petshop.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import voyager.petshop.dtos.Cart;
import voyager.petshop.dtos.CartItem;

@Service
public class HandleCartInCookieService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addItemInCartCookie(CartItem cartItem, String jsonCookieCart, HttpServletResponse response) throws IOException {
        Cart cart = getOrInitCartInCookie(jsonCookieCart, response);

        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);

        String newCartCookie = objectMapper.writeValueAsString(cart);

        String encodedCartCookie = encodeString(newCartCookie);

        Cookie cartCookie = new Cookie("jsonCookieCart", encodedCartCookie);
        cartCookie.setMaxAge(60 * 60 * 24 * 30); // One month
        cartCookie.setPath("/cart");
        cartCookie.setHttpOnly(true);
        response.addCookie(cartCookie);
    }

    public Cart getOrInitCartInCookie(String jsonCookieCart, HttpServletResponse response) throws IOException {
        if(jsonCookieCart == null || jsonCookieCart.isEmpty()) {
            Cart cart = new Cart(new ArrayList<>());

            String newCartCookie = objectMapper.writeValueAsString(cart);

            String encodedJson = encodeString(newCartCookie);

            Cookie cartCookie = new Cookie("jsonCookieCart", encodedJson);
            cartCookie.setMaxAge(60 * 60 * 24 * 30); // One month
            cartCookie.setPath("/cart");
            cartCookie.setHttpOnly(true);
            response.addCookie(cartCookie);
            return cart;
        }

        String decodedJson = decodeString(jsonCookieCart);

        return objectMapper.readValue(decodedJson, Cart.class);
    }

    // This methods below are hear because you can save data in cookie only if that data is encoded

    public String encodeString(String json) {
        return Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    public String decodeString(String encodedJson) {
        return new String(Base64.getUrlDecoder().decode(encodedJson), StandardCharsets.UTF_8);
    }

}
