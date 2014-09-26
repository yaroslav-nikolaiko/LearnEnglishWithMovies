package learn.english.core.realtime.service;

import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * Created by yaroslav on 9/26/14.
 */
@Stateful
public class LiveSubtitlesProcessor {
    int counter;
    public void execute(String time){
        System.out.println("Time : "+time);
        System.out.println("Counter : "+counter++);
    }

    @Remove
    public void destroy(){
        //NOP
    }
}
