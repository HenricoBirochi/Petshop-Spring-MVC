package voyager.petshop.authentication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.exceptions.NotLoggedInException;

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

        throw new NotLoggedInException("You can't do this, first you need to sign in");

    }

}
