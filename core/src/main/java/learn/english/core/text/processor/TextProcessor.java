package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.entity.WordCell;
import learn.english.core.service.DictionaryService;
import learn.english.core.utils.Category;
import learn.english.core.utils.Language;
import learn.english.parser.Parser;
import learn.english.parser.exception.ParserException;
import learn.english.translator.Translator;
import lombok.AllArgsConstructor;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yaroslav on 8/6/14.
 */
@AllArgsConstructor
public class TextProcessor {
    @EJB
    DictionaryService service;
    public void setCategory(@NotNull WordCell word, @NotNull Dictionary dictionary){
        //return Category.LEARNING_NOW;
    }

}

