package learn.english.parser.utils;

import org.apache.commons.configuration.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yaroslav on 8/25/14.
 */
public class ConfigurationManager {
    static  CompositeConfiguration configuration;
    static Map<String, Properties> cache = new HashMap<>();
    static{
        try {
            //DefaultConfigurationBuilder factory = new DefaultConfigurationBuilder("config-files-list.xml");
            //DefaultConfigurationBuilder factory = new DefaultConfigurationBuilder();
            configuration = new CompositeConfiguration();
            configuration.addConfiguration(new SystemConfiguration());
            PropertiesConfiguration core = new PropertiesConfiguration();
            core.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("core.properties"));
            configuration.addConfiguration(core);
            //configuration = factory.getInMemoryConfiguration();
        } catch (ConfigurationException e) {
            configuration = new CompositeConfiguration();
            e.printStackTrace();
        }
    }


    public static Properties load(String key) {
        return loadByFileName(configuration.getString(key));
    }

    public static String value(String key) {
        return configuration.getString(key);
    }

    static Properties loadByFileName(String name){
        if(name==null)
            return new Properties();
        if( name.startsWith("/") && name.length()>1)
            name = name.substring(1);

        if(cache.containsKey(name))
            return cache.get(name);

        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
            cache.put(name, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
