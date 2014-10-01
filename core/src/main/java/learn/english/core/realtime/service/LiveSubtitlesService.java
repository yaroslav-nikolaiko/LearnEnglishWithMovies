package learn.english.core.realtime.service;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;
import learn.english.core.service.DictionaryService;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.dto.AdvanceSubtitles;
import learn.english.model.dto.LiveSample;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.utils.LogTrace;
import learn.english.vlc.VlcStatusData;

import javax.ejb.EJB;
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
    @EJB
    DictionaryService dictionaryService;

    @PUT
    public Response put(VlcStatusData vlcStatus, @Context HttpHeaders httpHeaders) {
        String auth_token = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        String username = authenticationProvider.getUserName(auth_token);
        //liveContext.getLiveProcessor(username).execute(vlcStatus);
        //  liveContext.getLiveProcessor(username).setDictionary(dictionaryService.get(Long.valueOf(1)));
        liveContext.getLiveProcessor(username).vlcEvent(vlcStatus);
        return Response.ok().build();
    }

    @GET
    @Path("subtitles")
    public AdvanceSubtitles getSubtitles(@QueryParam(value = "dicID")String dicID, @Context HttpHeaders httpHeaders) {
        Long id = Long.valueOf(dicID);
        String auth_token = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        String username = authenticationProvider.getUserName(auth_token);
        LiveSubtitlesProcessor liveProcessor = liveContext.getLiveProcessor(username);
        if (liveProcessor.getDictionary() == null || ! liveProcessor.getDictionary().getId().equals(id))
            liveProcessor.setDictionary(dictionaryService.get(id));
        return liveProcessor.getAdvanceSubtitles();
    }

    @GET
    public LiveSample getSample(@QueryParam(value = "dicID")String dicID,@Context HttpHeaders httpHeaders){
        Long id = Long.valueOf(dicID);
        String auth_token = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
        String username = authenticationProvider.getUserName(auth_token);
        LiveSubtitlesProcessor liveProcessor = liveContext.getLiveProcessor(username);
        if (liveProcessor.getDictionary() == null || ! liveProcessor.getDictionary().getId().equals(id))
            liveProcessor.setDictionary(dictionaryService.get(id));
        return liveProcessor.sample();
    }
}
