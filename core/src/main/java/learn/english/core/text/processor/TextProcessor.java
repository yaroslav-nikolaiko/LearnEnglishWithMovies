package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.entity.WordCell;
import learn.english.core.service.DictionaryService;
import learn.english.core.utils.Category;
import learn.english.core.utils.Language;
import learn.english.parser.Parser;
import learn.english.parser.Text;
import learn.english.parser.exception.ParserException;
import learn.english.translator.Translator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/6/14.
 */

public class TextProcessor {


    public Collection<WordCell> computeWordCells(@NotNull MediaItem item, @NotNull Dictionary dictionary) {
        Text text = parseText(item);
        Set<String> words = text.words();
        Set<String> vocabulary = allWords(dictionary);
        Set<String> newWords = words.stream().filter(w-> ! vocabulary.contains(w)).collect(toSet());
        Set<String> oldWords = words.stream().filter(vocabulary::contains).collect(toSet());
        List<WordCell> list = new ArrayList<>();
        list.addAll(wordCellForOldWords(oldWords));
        list.addAll(wordCellforNewWords(newWords));

        return list;
    }


    public Category category(@NotNull WordCell wordCell, @NotNull Dictionary dictionary) {
/*        Set<String> words = allWords(dictionary);
        if(words.contains(wordCell.getWord()))
            wordCell.setCategory();*/
        return null;

    }

    private Set<String>  allWords(Dictionary dictionary){
        return dictionary.getMediaItems().stream().
                flatMap((item) -> item.getWords().stream()).
                map(WordCell::getWord).
                collect(toSet());
    }

    private Text parseText(MediaItem item){
        Text text = null;
        try {
            text = item.getParser().parse(item.getContent());
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return text;
    }

    private List<WordCell> wordCellForOldWords(Collection<String> words){

    }

    private List<WordCell> wordCellforNewWords(Collection<String> words){

    }









    /*    static ThreadLocal<Map<Dictionary, TextProcessor>> processors = ThreadLocal.withInitial(HashMap::new);
    Dictionary dictionary;
    Set<String> allWordsInDictionary;


    public TextProcessor(@NotNull Dictionary dictionary) {
        allWordsInDictionary = dictionary.getMediaItems().stream().
                flatMap((item)->item.getWords().stream()).
                map(WordCell::getWord).
                collect(toSet());
    }

    public static TextProcessor instance(Dictionary dictionary){
        Map<Dictionary, TextProcessor> map = processors.get();
        map.computeIfAbsent(dictionary, TextProcessor::generateProcessor);
        return map.get(dictionary);
    }

    static TextProcessor generateProcessor(Dictionary dictionary){
        return new TextProcessor(dictionary);
    }*/

}

