package yaroslav.learn.english.core.validation;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaroslav on 6/14/14.
 */
@InterceptorBinding
@Target( { TYPE, METHOD })
@Retention(RUNTIME)
public @interface ValidationHandlerEjb {
}
