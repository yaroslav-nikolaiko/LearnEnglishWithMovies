package yaroslav.learn.english.core.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaroslav on 7/23/14.
 */
@Constraint(validatedBy=ExistInDatabaseConstraint.class)
@Target({ METHOD, FIELD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface ExistInDB {
    String message() default "{yaroslav.learn.english.core.constraint.existInDb}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
