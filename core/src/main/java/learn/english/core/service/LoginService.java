package learn.english.core.service;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.User;
import learn.english.utils.LogTrace;

import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by yaroslav on 9/26/14.
 */
@Stateless
@Path("login")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
@ValidationHandlerEjb
@LogTrace
public class LoginService extends AbstractService<User>  {
    @Inject
    AuthenticationProvider authenticationProvider;

    @Inject
    public LoginService(EntityManager em) {
        super(User.class);
        this.em = em;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        try {
            String auth_token = authenticationProvider.login(name, password);
            return Response.ok().header(HTTPHeaderNames.AUTH_TOKEN, auth_token).build();
        } catch (LoginException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
