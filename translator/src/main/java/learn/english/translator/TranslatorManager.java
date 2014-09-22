package learn.english.translator;

import learn.english.translator.core.dao.factory.TranslatorDAOFactory;
import learn.english.translator.core.factory.TranslatorFactory;
import learn.english.translator.qualifier.storage.MongoDB;
import learn.english.translator.qualifier.service.Yandex;
import learn.english.translator.utils.Utils;
import learn.english.utils.ConfigurationManager;
import learn.english.utils.LogTrace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaroslav on 9/22/14.
 */
@Singleton
@Startup @LogTrace
public class TranslatorManager implements Serializable {
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    Map<String, Translator> pool = new ConcurrentHashMap<>();

    @Inject @MongoDB
    private TranslatorDAOFactory translatorDAOFactory;
    @Inject @Yandex
    private TranslatorFactory translatorFactory;

    @PostConstruct
    void init(){
        try {
            String http_proxy_str = System.getenv("http_proxy");
            String https_proxy_str = System.getenv("https_proxy");
            if(http_proxy_str!=null && ! http_proxy_str.isEmpty()){
                URI http_proxy = new URI(http_proxy_str);
                System.setProperty("http.proxyHost", http_proxy.getHost());
                System.setProperty("http.proxyPort", String.valueOf(http_proxy.getPort()));
                logger.info("Setting http.proxyHost = {}, http.proxyPort = {}",http_proxy.getHost(), http_proxy.getPort());
            }
            if(https_proxy_str!=null && ! https_proxy_str.isEmpty()){
                URI https_proxy = new URI(https_proxy_str);
                System.setProperty("https.proxyHost", https_proxy.getHost());
                System.setProperty("https.proxyPort", String.valueOf(https_proxy.getPort()));
                logger.info("Setting https.proxyHost = {}, https.proxyPort = {}",https_proxy.getHost(), https_proxy.getPort());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    void persist() {
        translatorDAOFactory.onDestroy();
    }

    public Translator translator(String languageFrom, String languageTo) {
        String dataBaseName = Utils.dataBaseName(languageFrom, languageTo);
        Translator translator = pool.get(dataBaseName);
        if(translator==null){
            translator = translatorFactory.getInstance(translatorDAOFactory.getInstance(dataBaseName));
            pool.put(dataBaseName, translator);
        }
        return translator;
    }

}
