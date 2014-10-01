package learn.english.vlc;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by yaroslav on 9/27/14.
 */
public class VlcRestClient {
    private static final String SERVICE_HOST = "http://localhost";
    private static final Integer SERVICE_PORT = 8888;
    private static final String PATH = "requests/status.xml";
    protected URI uri = UriBuilder.fromUri(SERVICE_HOST).port(SERVICE_PORT).path(PATH).build();
    protected Client client = ClientBuilder.newClient();

    private static final String name = "";
    private static final String password = "1975";

    public VlcRestClient(){
        client = ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(name, password);
        client.register(feature);
    }

    public VlcStatusData getStatus(){
        return unmarshaller();
    }


    VlcStatusData unmarshaller(){
        VlcStatusData result = null;
        byte[] content = getContent();
        if(content==null)
            return null;
        InputStream is = new ByteArrayInputStream(content);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(VlcStatusData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (VlcStatusData) jaxbUnmarshaller.unmarshal(is);
            is.close();
        }  catch (JAXBException e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }


    byte[] getContent(){
        try {
            return client.target(uri).request().get(byte[].class);
        }catch (Exception e){
            return null;
        }

    }

}
