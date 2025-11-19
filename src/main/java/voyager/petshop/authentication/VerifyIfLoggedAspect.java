package voyager.petshop.authentication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VerifyIfLoggedAspect {

    @Around("@annotation(VerifyIfLogged)")
    public Object verifyLogin(ProceedingJoinPoint jp) throws Throwable {



        var result = jp.proceed();
        return result;
    }

}
