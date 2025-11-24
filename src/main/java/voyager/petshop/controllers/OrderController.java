package voyager.petshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.authentication.VerifyIfLogged;
import voyager.petshop.models.Order;
import voyager.petshop.models.User;
import voyager.petshop.repositories.OrderRepository;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @VerifyIfLogged
    @GetMapping
    public ModelAndView listOrders(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("order/list_orders");
        
        var session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
            mv.addObject("orders", orders);
        }
        
        return mv;
    }

}
