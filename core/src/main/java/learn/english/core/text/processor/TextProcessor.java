package learn.english.core.text.processor;

import learn.english.parser.Parser;
import learn.english.parser.exception.ParserException;
import learn.english.translator.Translator;
import lombok.AllArgsConstructor;

import java.util.Set;

/**
 * Created by yaroslav on 8/6/14.
 */
@AllArgsConstructor
public class TextProcessor {
    Parser parser;
    Translator translator;

    Set<String> decompose(byte[] content){
        try {
            return parser.parse(content).words();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    String rootWord(String word) {
        return translator.rootWord(word);
    }

    String definition(String word) {
        return translator.definition(word);
    }

    String translate(String text) {
        return translator.translate(text);
    }
}

