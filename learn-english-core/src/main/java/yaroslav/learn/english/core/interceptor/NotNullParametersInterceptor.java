package yaroslav.learn.english.core.interceptor;

import yaroslav.learn.english.core.exception.EntityIllegalArgumentsException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.lang.reflect.Method;

/**
 * Created by yaroslav on 6/14/14.
 */
@Interceptor
@NotNullParameters
public class NotNullParametersInterceptor {

    @AroundInvoke
    Object perform(InvocationContext ic) throws Exception{
        Class<?>[] parameterTypes = ic.getMethod().getParameterTypes();
        Object[] parameters = ic.getParameters();
        for(int i=0; i<parameterTypes.length; i++){
            if(parameters[i]==null)
                throw new ValidationException(String.format("%s cannot be NULL",parameterTypes[i].getSimpleName()));
        }
        return ic.proceed();
    }
}
