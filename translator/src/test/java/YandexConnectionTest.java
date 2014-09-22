import learn.english.translator.Translator;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by yaroslav on 8/3/14.
 */
public class YandexConnectionTest {

    @Test
    @Ignore
    public void $(){


/*        try {
            String http_proxy_str = System.getenv("http_proxy");
            String https_proxy_str = System.getenv("https_proxy");
            if(http_proxy_str!=null && ! http_proxy_str.isEmpty()){
                URI http_proxy = new URI(http_proxy_str);
                System.setProperty("http.proxyHost", http_proxy.getHost());
                System.setProperty("http.proxyPort", String.valueOf(http_proxy.getPort()));

                System.out.println("host = "+http_proxy.getHost());
                System.out.println("port = "+http_proxy.getPort());
            }
            if(https_proxy_str!=null && ! https_proxy_str.isEmpty()){
                URI https_proxy = new URI(https_proxy_str);
                System.setProperty("https.proxyHost", https_proxy.getHost());
                System.setProperty("https.proxyPort", String.valueOf(https_proxy.getPort()));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String word = "rebelling";
        Translator translator = new YandexTranslatorOld("en", "ru", new Properties());
        System.out.println(translator.translate(word));*/
    }

}
