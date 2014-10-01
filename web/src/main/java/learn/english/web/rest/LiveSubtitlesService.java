package learn.english.web.rest;

import learn.english.model.dto.AdvanceSubtitles;
import learn.english.model.dto.LiveSample;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/29/14.
 */
public class LiveSubtitlesService implements Serializable {
    @Inject
    RestService restService;

    public AdvanceSubtitles getSubtitles(String auth_token, String dictionaryID){
        Response response = restService.path("live").path("subtitles").param("dicID",dictionaryID).target.request(MediaType.APPLICATION_JSON).header("auth_token",auth_token).get();
        restService.target = null;
        return response.readEntity(AdvanceSubtitles.class);
    }

    public LiveSample getSample(String auth_token, String dictionaryID){
        Response response = restService.path("live").param("dicID",dictionaryID).target.request(MediaType.APPLICATION_JSON).header("auth_token",auth_token).get();
        restService.target = null;
        return response.readEntity(LiveSample.class);
    }
}
