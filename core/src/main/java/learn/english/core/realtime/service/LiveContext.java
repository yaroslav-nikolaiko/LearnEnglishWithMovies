package learn.english.core.realtime.service;

import learn.english.core.authentication.SessionExpired;

import javax.ejb.Remove;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 9/26/14.
 */
@ApplicationScoped
public class LiveContext {
    static final String MODULE_NAME = "lingvo-movie-core";
    //key - username
    final Map<String, LiveSubtitlesProcessor> data = new HashMap<>();

    public LiveSubtitlesProcessor getLiveProcessor(String username){
        if(data.containsKey(username))
            return data.get(username);
        try {
            InitialContext ic = new InitialContext();
            LiveSubtitlesProcessor liveProcessor =(LiveSubtitlesProcessor) ic.lookup(String.format("java:global/%s/%s",MODULE_NAME, LiveSubtitlesProcessor.class.getSimpleName()));
            data.put(username, liveProcessor);
            return liveProcessor;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(@Observes @SessionExpired String username){
        LiveSubtitlesProcessor processor = data.remove(username);
        if(processor!=null)
            processor.destroy();
    }

}
