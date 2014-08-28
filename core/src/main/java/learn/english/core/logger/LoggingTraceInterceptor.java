package learn.english.core.logger;

import learn.english.core.entity.User;
import learn.english.parser.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by yaroslav on 8/28/14.
 */
@Interceptor
@Loggable
@Priority(10000)
public class LoggingTraceInterceptor {
    final static Logger logger = LogManager.getLogger(ConfigurationManager.value("logger", User.class));

    @AroundInvoke
    public Object logMethod(InvocationContext ic){
        Object[] parameters = ic.getParameters();
        logger.trace("entering {} with parameters: {}",ic.getMethod().getName(), parameters);
        Object result = null;
        try {
            result = ic.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if( result != null)
                logger.trace("exit {} with result: {}",ic.getMethod().getName(), result);
            else
                logger.trace("exit {}",ic.getMethod().getName());
        }
        return result;
    }
}
