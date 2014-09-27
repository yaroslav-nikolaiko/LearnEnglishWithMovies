package learn.english.vlc;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * Created by yaroslav on 9/27/14.
 */
public class VlcRestClientTest {

    @Test
    @Ignore
    public void vlcServerConnection() throws Exception {
        VlcRestClient client = new VlcRestClient();
        VlcStatusData vlcStatusData = client.getStatus();
        if(vlcStatusData ==null){
            System.out.println("Fail to download status.xml from Vlc Server");
            return;
        }

        JAXBContext jc = JAXBContext.newInstance(VlcStatusData.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(vlcStatusData, System.out);
    }
}
