package yaroslav.learn.english.web.interceptor;

import org.primefaces.context.RequestContext;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;
import yaroslav.learn.english.web.exception.WebException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/15/14.
 */
@Interceptor
@DialogValidation
public class DialogValidationInterceptor implements Serializable {
    @AroundInvoke
    Object perform(InvocationContext ic) throws Exception {
        Object proceed = null;
        boolean crossValidation = false;
        try {
            proceed = ic.proceed();
            crossValidation = true;
        }finally {
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("crossValidation", crossValidation);
        }

        return proceed;
    }

}
