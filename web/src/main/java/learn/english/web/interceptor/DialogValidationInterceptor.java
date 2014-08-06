package learn.english.web.interceptor;

import org.primefaces.context.RequestContext;

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
