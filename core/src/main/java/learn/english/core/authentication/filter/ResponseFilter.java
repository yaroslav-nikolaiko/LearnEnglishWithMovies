package learn.english.core.authentication.filter;

import learn.english.core.authentication.HTTPHeaderNames;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by yaroslav on 9/26/14.
 */
@Provider
@PreMatching
public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add( "Access-Control-Allow-Headers", HTTPHeaderNames.AUTH_TOKEN );
    }
}
