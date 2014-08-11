package learn.english.core.text.processor;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Properties;

/**
 * Created by yaroslav on 8/11/14.
 */
@Singleton
@Startup
public class MostCommonWords {
    Properties most_common_100_words;
    Properties most_common_6000_words;

    @PostConstruct
    void init(){

    }



}
