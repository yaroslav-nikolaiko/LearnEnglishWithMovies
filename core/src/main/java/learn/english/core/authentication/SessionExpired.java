package learn.english.core.authentication;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaroslav on 9/27/14.
 */
@Qualifier
@Target( { FIELD, METHOD, PARAMETER  })
@Retention(RUNTIME)
public @interface SessionExpired {
}
