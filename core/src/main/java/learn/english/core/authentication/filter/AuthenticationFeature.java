package learn.english.core.authentication.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

/**
 * Created by yaroslav on 9/26/14.
 */
@Provider
public class AuthenticationFeature implements DynamicFeature {
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        // IN case we want to use Filter only for specific methods, we remove anotations @Provider and @PreMatching
        // from filter (and add @Priority(Priorities.AUTHENTICATION)) and specify in here
        // to which methods we want to apply that filter.


/*        Method method = resourceInfo.getResourceMethod();
        if (! method.isAnnotationPresent(AuthenticationRequired.class)) {
            AuthenticationFilter authenticationFilter =
                    new AuthenticationFilter(authenticationProvider);
            context.register(authenticationFilter);
        }*/


    }
}
