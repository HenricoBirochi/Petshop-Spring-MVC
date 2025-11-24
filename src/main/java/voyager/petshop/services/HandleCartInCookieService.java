package voyager.petshop.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import voyager.petshop.dtos.Cart;
import voyager.petshop.dtos.CartItem;

@Service
public class HandleCartInCookieService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addItemInCartCookie(String jsonCookieCart, CartItem cartItem, HttpServletResponse response) throws IOException {
        Cart cart = getOrInitCartInCookie(jsonCookieCart, response);

        List<CartItem> cartItems = cart.getCartItems();
        cartItem.setTotalPriceItem();
        cartItems.add(cartItem);

        createOrUpdateCartCookie(cart, response);
    }

    public void removeItemFromCartCookie(String jsonCookieCart, UUID cartItemId, HttpServletResponse response) throws IOException {
        Cart cart = getOrInitCartInCookie(jsonCookieCart, response);

        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getCartItemId().equals(cartItemId)) {
                cartItems.remove(cartItem);
            }
            if (cartItems.size() == 0) {
                break;
            }
        }

        createOrUpdateCartCookie(cart, response);
    }

    public Cart getOrInitCartInCookie(String jsonCookieCart, HttpServletResponse response) throws IOException {
        if(jsonCookieCart == null || jsonCookieCart.isEmpty()) {
            Cart cart = new Cart(new ArrayList<>());

            createOrUpdateCartCookie(cart, response);

            return cart;
        }

        String decodedJson = decodeString(jsonCookieCart);

        return objectMapper.readValue(decodedJson, Cart.class);
    }

    public void createOrUpdateCartCookie(Cart cart, HttpServletResponse response) throws JsonProcessingException {
        String newCartCookie = objectMapper.writeValueAsString(cart);

        String encodedCartCookie = encodeString(newCartCookie);

        Cookie cartCookie = new Cookie("jsonCookieCart", encodedCartCookie);
        cartCookie.setMaxAge(60 * 60 * 24 * 7); // One month
        cartCookie.setPath("/cart");
        cartCookie.setHttpOnly(true);
        response.addCookie(cartCookie);
    }

    public void clearCartCookie(HttpServletResponse response) {
        Cookie cartCookie = new Cookie("jsonCookieCart", "");
        cartCookie.setMaxAge(0); // '0' to clear the cookie
        cartCookie.setPath("/cart");
        cartCookie.setHttpOnly(true);
        response.addCookie(cartCookie);
    }

    // This methods below are hear because you can save data in cookie only if that data is encoded

    public String encodeString(String json) {
        return Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    public String decodeString(String encodedJson) {
        return new String(Base64.getUrlDecoder().decode(encodedJson), StandardCharsets.UTF_8);
    }

}
