package voyager.petshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/cart")
public class CartController {


    @GetMapping("")
    public ModelAndView getMethodName() {
        var mv = new ModelAndView("");
        return mv;
    }

    @PostMapping("/add-item")
    public ModelAndView postMethodName() {
        var mv = new ModelAndView("");
        return mv;
    }

}
