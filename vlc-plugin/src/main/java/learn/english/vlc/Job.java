package learn.english.vlc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yaroslav on 9/25/14.
 */
public class Job {
    int counter;
    boolean terminated;
    Timer timer;
    SendRequest task;
    RestClient client = new RestClient();

    public void start(){
        terminated = false;
        timer = new Timer();
        task = new SendRequest();
        timer.schedule(task, 0, 2000);
    }

    public void stop(){
        terminated = true;
    }

    class SendRequest extends TimerTask {
        public void run() {
            if(terminated)
                cancel();
            client.execute(new TransferData(String.valueOf(counter++)));
        }
    }
}
