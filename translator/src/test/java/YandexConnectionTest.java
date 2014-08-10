import learn.english.translator.Translator;
import learn.english.translator.core.YandexTranslator;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by yaroslav on 8/3/14.
 */
public class YandexConnectionTest {

    @Test
    @Ignore
    public void $(){
        System.setProperty("https.proxyHost", "proxy2.cht");
        System.setProperty("https.proxyPort", "3128");
        String word = "rebelling";
        Translator translator = new YandexTranslator("en", "ru", new Properties());
        System.out.println(translator.translate(word));
    }

}
