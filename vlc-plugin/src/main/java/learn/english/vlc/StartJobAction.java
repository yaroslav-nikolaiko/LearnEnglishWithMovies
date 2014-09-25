package learn.english.vlc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yaroslav on 9/25/14.
 */
public class StartJobAction implements ActionListener {
    int counter;
    RestClient client = new RestClient();
    @Override
    public void actionPerformed(ActionEvent e) {
        client.execute(new TransferData(String.valueOf(counter++)));
    }
}
