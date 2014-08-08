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
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/6/14.
 */
@Stateless
public class TextProcessor {

    public void computeWordCells(@NotNull MediaItem item, @NotNull Dictionary dictionary) {
        Text text = parseText(item);
        Map<String, WordCell> vocabulary = allWords(dictionary).stream().collect(toMap(WordCell::getWord, cell -> cell));

        item.setWords(
                text.words().stream().map(w ->
                vocabulary.containsKey(w) ?
                        updateExistedWordCell(vocabulary.get(w), item) :
                        generateNewWordCell(w, item, dictionary)).
                collect(toList())
        );
    }


    public Category category(@NotNull WordCell wordCell, @NotNull Dictionary dictionary) {
        return Category.LEARNING_NOW;
    }

/*    private Stream<WordCell> allWords(Dictionary dictionary){
        return dictionary.getMediaItems().stream().
                flatMap((item) -> item.getWords().stream());
    }*/

    public Set<WordCell> allWords(Dictionary dictionary){
        //return dictionary.getMediaItems().stream().flatMap(item -> item.getWords().stream()).collect(toList());
        List<MediaItem> items = dictionary.getMediaItems();
        Set<WordCell> cells = new HashSet<>();
        for (MediaItem item : items) {
            cells.addAll(item.getWords());
        }
        return cells;
    }

    private Text parseText(MediaItem item){
        Text text = null;
        try {
            text = ParserProducer.getParser(item).parse(item.getContent());
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return text;
    }

    private WordCell generateNewWordCell(String word, MediaItem item, Dictionary dictionary) {
        WordCell wordCell = new WordCell(word);
        wordCell.setCategory(category(wordCell, dictionary));
        wordCell.setMediaItems(Arrays.asList(item));
        return wordCell;
    }

    private WordCell updateExistedWordCell(WordCell wordCell, MediaItem item) {
        wordCell.getMediaItems().add(item);
        return wordCell;
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

