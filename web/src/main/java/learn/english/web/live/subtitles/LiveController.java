package learn.english.web.live.subtitles;

import learn.english.model.dto.AdvanceSubtitles;
import learn.english.model.dto.LiveSample;
import learn.english.model.entity.Dictionary;
import learn.english.web.rest.LiveSubtitlesService;
import learn.english.web.rest.RestService;
import lombok.Getter;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yaroslav on 9/27/14.
 */
@Named
@SessionScoped
public class LiveController implements Serializable {
    @Inject LoginForm loginForm;
    @Inject RestService restService;
    @Inject LiveSubtitlesService subtitlesService;
    @Getter
    AdvanceSubtitles subtitles;
    @Getter
    LiveSample sample;

    String auth_token;
    //TODO
    Dictionary currentDictionary;

    public void login() {
        auth_token = restService.login(loginForm.getForm());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(String.format("openSocket('%s')",auth_token ));
    }

    public boolean isNotLoggedIn(){
        return auth_token==null;
    }

    public void update(){
        if(subtitles == null){
            getSubtitlesFromServer();
            if(subtitles==null)
                return;
        }
        sample = subtitlesService.getSample(auth_token, "1");
        if(! sample.getVideoFileName().equals(subtitles.getVideoFileName())){
            subtitles=null;
            update();
        }
        RequestContext context = RequestContext.getCurrentInstance();

        Integer nextTimeFrame = subtitles.getData().higherKey(sample.getTimeFrame());
        nextTimeFrame = nextTimeFrame!=null ? nextTimeFrame : sample.getTimeFrame();
        Integer previousTimeFrame = subtitles.getData().lowerKey(sample.getTimeFrame());
        if(previousTimeFrame!=null)
            previousTimeFrame = subtitles.getData().lowerKey(previousTimeFrame);
        previousTimeFrame = previousTimeFrame!=null ? previousTimeFrame : sample.getTimeFrame();

        context.execute(String.format("scrollToTimeFrame(%s, %s, %s)", previousTimeFrame,
                sample.getTimeFrame(), nextTimeFrame));

    }

    public Collection<AdvanceSubtitles.Unit> getValues(){
        if(subtitles==null)
            return null;
        return subtitles.getData().values();
    }


    void getSubtitlesFromServer() {
        subtitles = subtitlesService.getSubtitles(auth_token, "1");
/*        if(subtitles==null){
            try {
            for(int i=0; i<2; i++){
                Thread.sleep(2000);
                subtitles = subtitlesService.getSubtitles(auth_token, "1");
                if(subtitles!=null)
                    break;
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

}
