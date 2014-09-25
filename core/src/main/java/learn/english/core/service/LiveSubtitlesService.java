package learn.english.core.service;

import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.utils.LogTrace;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by yaroslav on 9/25/14.
 */
@Stateless
@Path("/live")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
@ValidationHandlerEjb
@LogTrace
public class LiveSubtitlesService {
    @GET
    @Path("{time}")
    public Response get(@PathParam("time") String time) {
        System.out.println(time);
        return Response.ok().build();
    }
}
