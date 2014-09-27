package learn.english.vlc;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by yaroslav on 9/27/14.
 */
public class JaxBTest {

    @Test
    @Ignore
    public void unmarshal() throws Exception{
        JAXBContext jc = JAXBContext.newInstance(VlcStatusData.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        VlcStatusData data = (VlcStatusData) unmarshaller.unmarshal(new File("src/test/resources/vlc-output.xml"));

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(data, System.out);
    }
}
