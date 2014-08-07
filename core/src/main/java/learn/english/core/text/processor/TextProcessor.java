package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.service.DictionaryService;
import learn.english.core.utils.Category;
import learn.english.core.utils.Language;
import learn.english.parser.Parser;
import learn.english.parser.exception.ParserException;
import learn.english.translator.Translator;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yaroslav on 8/6/14.
 */
@AllArgsConstructor
public class TextProcessor {
    Dictionary dictionary;
    //DictionaryService service;
    static ThreadLocal<Map<Dictionary, TextProcessor>> processors = ThreadLocal.withInitial(HashMap::new);

    public static TextProcessor getProcessor(Dictionary dictionary){
        Map<Dictionary, TextProcessor> map = processors.get();
        map.computeIfAbsent(dictionary, TextProcessor::generateProcessor);
        return map.get(dictionary);
    }

    public Category wordCategory(String word){
        return Category.LEARNING_NOW;
    }

    static TextProcessor generateProcessor(Dictionary dictionary){
        return new TextProcessor(dictionary);
    }
}

