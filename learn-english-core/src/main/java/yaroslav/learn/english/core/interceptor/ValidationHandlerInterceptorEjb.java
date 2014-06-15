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

            EJBIllegalArgumentsException myException = new EJBIllegalArgumentsException("Constraint Violations in EJB module ", e);
            myException.setExplanation(e.getMessage());
            myException.setMessageType(EJBIllegalArgumentsException.MessageType.INFO);
            throw myException;

        }
        catch(EJBIllegalArgumentsException e) {throw e;}
        catch (Exception e) {e.printStackTrace(); /*log*/ }
        return null;
    }
}
