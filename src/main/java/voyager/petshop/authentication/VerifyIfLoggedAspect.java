package voyager.petshop.authentication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class VerifyIfLoggedAspect {

    @Around("@annotation(VerifyIfLogged)")
    public Object verifyLogin(ProceedingJoinPoint jp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        var session = request.getSession();

        var user = session.getAttribute("user");

        if (user != null) {
            return jp.proceed();
        }

        return new ModelAndView("redirect:/user/sign-in");

    }

}
