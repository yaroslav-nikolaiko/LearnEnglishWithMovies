package learn.english.core.realtime.service;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.utils.LogTrace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
    @Inject
    LiveContext liveContext;
    @Inject
    AuthenticationProvider authenticationProvider;
    @GET
    @Path("{time}")
    public Response get(@PathParam("time") String time, @Context HttpHeaders httpHeaders) {
        String auth_token = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        String username = authenticationProvider.getUserName(auth_token);
        liveContext.getLiveProcessor(username).execute(time);
        return Response.ok().build();
    }

    @PUT
    public Response put(String time, @Context HttpHeaders httpHeaders) {
        String auth_token = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        String username = authenticationProvider.getUserName(auth_token);
        liveContext.getLiveProcessor(username).execute(time);
        return Response.ok().build();
    }
}
