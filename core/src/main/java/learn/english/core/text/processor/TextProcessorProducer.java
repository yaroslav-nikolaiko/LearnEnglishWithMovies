package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.utils.Category;

import javax.enterprise.inject.Produces;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 8/7/14.
 */
public class TextProcessorProducer {
/*    static ThreadLocal<Map<Dictionary, TextProcessor>> processors = ThreadLocal.withInitial(HashMap::new);

    @Produces
    public static TextProcessor getProcessor(Dictionary dictionary){
        Map<Dictionary, TextProcessor> map = processors.get();
        map.computeIfAbsent(dictionary, TextProcessor::generateProcessor);
        return map.get(dictionary);
    }

    static TextProcessor generateProcessor(Dictionary dictionary){
        return new TextProcessor(dictionary);
    }*/
}
