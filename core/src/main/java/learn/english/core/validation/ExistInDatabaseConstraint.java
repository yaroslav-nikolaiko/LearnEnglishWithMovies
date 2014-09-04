package learn.english.core.validation;

import learn.english.model.utils.Persistent;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by yaroslav on 7/23/14.
 */
public class ExistInDatabaseConstraint implements ConstraintValidator<ExistInDB, Persistent> {
    @Inject
    EntityManager em;

    @Override
    public void initialize(ExistInDB constraintAnnotation) {

    }

    @Override
    public boolean isValid(Persistent entity, ConstraintValidatorContext context) {
        if(entity==null)
            return false;
        if(entity.getId()==null)
            return false;
        return em.find(entity.getClass(), entity.getId()) != null;
    }
}
