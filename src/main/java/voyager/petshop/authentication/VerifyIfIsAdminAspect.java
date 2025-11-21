package voyager.petshop.authentication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import voyager.petshop.exceptions.NotAdminException;
import voyager.petshop.exceptions.NotLoggedInException;
import voyager.petshop.models.User;
import voyager.petshop.models.enums.UserRoles;

@Aspect
@Component
public class VerifyIfIsAdminAspect {

    @Around("@annotation(VerifyIfIsAdmin)")
    public Object verifyLogin(ProceedingJoinPoint jp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        var session = request.getSession();

        User user = (User)session.getAttribute("user");

        if (user == null) {
            throw new NotLoggedInException("You can't access this page, first you need to sign in");
        }

        if (user.getUserRole().equals(UserRoles.ADMIN)) {
            return jp.proceed();
        }

        throw new NotAdminException("You can't access this page, you aren't a Admin");

    }

}
