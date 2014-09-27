package learn.english.core.realtime.service;

import learn.english.vlc.VlcStatusData;

import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * Created by yaroslav on 9/26/14.
 */
@Stateful
public class LiveSubtitlesProcessor {
    int counter;
    public void execute(VlcStatusData vlcStatus){
        System.out.println("Time : "+vlcStatus.getTime());
        System.out.println("Counter : "+counter++);
    }

    @Remove
    public void destroy(){
        //NOP
    }
}
