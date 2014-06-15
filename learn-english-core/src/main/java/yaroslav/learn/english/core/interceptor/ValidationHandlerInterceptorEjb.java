package yaroslav.learn.english.core.interceptor;

import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/14/14.
 */
@Interceptor
@ValidationHandlerEjb
@Priority(1010)
public class ValidationHandlerInterceptorEjb implements Serializable {

    @AroundInvoke
    Object perform(InvocationContext ic) throws EJBIllegalArgumentsException {
        try {
            return ic.proceed();
        } catch (ConstraintViolationException e) {
            //throw new Error("Its Fucking works! from EJB");
            throw new EJBIllegalArgumentsException("It's fucking work from ejb", e);
        }catch(EJBIllegalArgumentsException e) {
            throw e;
        }
        catch (Exception e) {
            //log
            e.printStackTrace();
        }
        return null;
    }
}
