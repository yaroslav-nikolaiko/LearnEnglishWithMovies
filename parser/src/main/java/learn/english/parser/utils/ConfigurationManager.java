package learn.english.parser.utils;

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
    static final String DEFAULT_CONFIG_FILE = "/configuration.properties";
    static Map<URL, Properties> cache = new HashMap<>();

    public static Properties load(Class module) {
        return load(DEFAULT_CONFIG_FILE, module);
    }

    public static Properties load(String name, Class module) {
        return loadByFileName(name, module);
    }

    public static Properties loadProperty(String property, Class module){
        Properties config = load(module);
        return loadByFileName(config.getProperty(property), module);
    }

    public static String value(String key, Class module){
        ClassLoader classLoader = module.getClassLoader();
        String mname = null;
        try {
            mname = (String) new InitialContext().lookup("java:module/ModuleName");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        URL url = module.getResource(DEFAULT_CONFIG_FILE);
        Properties properties = null;
        if(cache.containsKey(url))
            properties =  cache.get(url);
        else {
            properties = new Properties();
            try {
                properties.load(url.openConnection().getInputStream());
                cache.put(url, properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

    static Properties loadByFileName(String name, Class module){
        if(name==null || module==null)
            return new Properties();
        if( ! name.startsWith("/"))
            name = "/" + name;
        URL url = module.getResource(name);
        if(cache.containsKey(url))
            return cache.get(url);

        Properties properties = new Properties();
        try {
            properties.load(url.openConnection().getInputStream());
            cache.put(url, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
