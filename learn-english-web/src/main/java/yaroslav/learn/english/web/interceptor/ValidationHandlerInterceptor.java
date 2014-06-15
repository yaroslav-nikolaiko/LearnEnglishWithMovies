package yaroslav.learn.english.web.interceptor;

import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/14/14.
 */
@Interceptor
@ValidationHandler
public class ValidationHandlerInterceptor implements Serializable {

    @AroundInvoke
    Object perform(InvocationContext ic) {
        try {
            return ic.proceed();
        } catch (EJBIllegalArgumentsException e) {

            //throw new Error("It's fucking working from interceptor",e);

//            if(e.getCause().getClass()==ConstraintViolationException.class){
//                ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
//                FacesContext context = FacesContext.getCurrentInstance();
//                FacesMessage message = new FacesMessage(e.getMessage());
//                context.addMessage(null, message);
//            }else
//                e.printStackTrace();

        } catch (Exception e) {
            //log
            e.printStackTrace();
        }
        return null;
    }
}
