package learn.english.core.service;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;
import learn.english.core.validation.ExistInDB;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.User;
import learn.english.utils.LogTrace;
import lombok.NoArgsConstructor;

import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by yaroslav on 6/8/14.
 */
@Stateless
@Path("/user")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
@ValidationHandlerEjb @LogTrace @NoArgsConstructor
public class UserService extends AbstractService<User> {
    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    public UserService(EntityManager em) {
        super(User.class);
        this.em = em;
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        try {
            String auth_token = authenticationProvider.login(name, password);
            return Response.ok().header(HTTPHeaderNames.AUTH_TOKEN, auth_token).build();
        } catch (LoginException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    public Response addToDataBase(User entity) {
        return super.addToDataBase(entity);
    }

    @GET
    public User findByNameAndPassword(@QueryParam(value = "name")String name, @QueryParam(value = "password") String password) {
        return singeResult(User.FIND_BY_NAME_AND_PASSWORD, name, password);
    }

    @GET
    @Path("name/{name}")
    public Boolean nameExist(@PathParam("name") String name){
        Query query = buildSimpleQuery(User.COUNT_BY_NAME, name);
        return (Long) query.getSingleResult() > 0;
    }

    @GET
    @Path("email/{email}")
    public Boolean emailExist(@PathParam("email")String email){
        Query query = buildSimpleQuery(User.COUNT_BY_EMAIL, email);
        return (Long) query.getSingleResult() > 0;
    }

    public String getPassword(String name){
        return singeResult(User.FIND_BY_NAME, name).getPassword();
    }


}
