package learn.english.core.authentication.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaroslav on 9/26/14.
 */
@NameBinding
@Target( { TYPE, METHOD })
@Retention(RUNTIME)
public @interface AuthenticationRequired {
}
