package learn.english.core.service;

import learn.english.core.validation.ExistInDB;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.User;
import learn.english.utils.LogTrace;

import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.*;
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
@ValidationHandlerEjb @LogTrace
@ApplicationException(rollback = false)
public class UserService extends AbstractService<User> {

    @Inject
    public UserService(EntityManager em) {
        super(User.class);
        this.em = em;
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
    public String nameExist(@PathParam("name") String name){
        Query query = buildSimpleQuery(User.COUNT_BY_NAME, name);
        return String.valueOf((Long) query.getSingleResult() > 0);
    }

    @GET
    @Path("email/{email}")
    public String emailExist(@PathParam("email")String email){
        Query query = buildSimpleQuery(User.COUNT_BY_EMAIL, email);
        return String.valueOf((Long) query.getSingleResult() > 0);
    }


}
