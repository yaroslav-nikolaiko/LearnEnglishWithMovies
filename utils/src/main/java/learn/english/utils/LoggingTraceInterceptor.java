package learn.english.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Created by yaroslav on 8/28/14.
 */
@Interceptor
@LogTrace
@Priority(10000)
public class LoggingTraceInterceptor implements Serializable{
    final static Logger logger = LogManager.getLogger(ConfigurationManager.value("trace-logger"));

    @AroundInvoke
    public Object logMethod(InvocationContext ic){
        Object[] parameters = ic.getParameters();
        if(parameters!=null && parameters.length>0)
            logger.trace("entering {} : [{}({})]",ic.getTarget().getClass().getName(), ic.getMethod().getName(), parameters);
        else
            logger.trace("entering {} : [{}()]",ic.getTarget().getClass().getName(), ic.getMethod().getName());
        Object result = null;
        try {
            result = ic.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }/*finally {
            if( result != null)
                logger.trace("exit {} with result: {}", ic.getMethod().getName(), result);
            else
                logger.trace("exit {}", ic.getMethod().getName());
        }*/
        return result;
    }
}
