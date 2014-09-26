package learn.english.core.authentication.filter;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by yaroslav on 9/26/14.
 */
//@Priority(Priorities.AUTHENTICATION)
@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject
    AuthenticationProvider authenticationProvider;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if ( requestContext.getRequest().getMethod().equals( "OPTIONS" ) ) {
            requestContext.abortWith( Response.status(Response.Status.OK).build() );
            return;
        }
        String path = requestContext.getUriInfo().getPath();

        // For any pther methods besides login, the authToken must be verified
        //if ( !path.startsWith( "/demo-business-resource/login/" ) ) {}

    }
}
