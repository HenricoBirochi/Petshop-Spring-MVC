package voyager.petshop.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import voyager.petshop.exceptions.NotAdminException;
import voyager.petshop.exceptions.NotLoggedInException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotLoggedInException.class)
    public ModelAndView handleLoginException(NotLoggedInException ex) {
        ModelAndView mv = new ModelAndView("error/error");
        String error = ex.getMessage();
        mv.addObject("error", error);
        return mv;
    }

    @ExceptionHandler(NotAdminException.class)
    public ModelAndView handleAdminException(NotAdminException ex) {
        ModelAndView mv = new ModelAndView("error/error");
        String error = ex.getMessage();
        mv.addObject("error", error);
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralExceptions(Exception ex) {
        ModelAndView mv = new ModelAndView("error/error");
        String error = ex.getMessage();
        mv.addObject("error", error);
        return mv;
    }

}
