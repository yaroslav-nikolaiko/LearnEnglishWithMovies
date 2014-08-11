package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.entity.WordCell;
import learn.english.core.utils.Category;
import learn.english.parser.Text;
import learn.english.parser.exception.ParserException;
import learn.english.translator.Translator;
import learn.english.translator.core.TranslatorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/6/14.
 */
@Stateless
public class TextProcessor {
    @EJB
    TranslatorManager translatorManager;

    public void computeWordCells(@NotNull MediaItem item, @NotNull Dictionary dictionary) {
        Text text = parseText(item);
        Map<String, WordCell> vocabulary = allWords(dictionary).stream().collect(toMap(WordCell::getWord, cell -> cell));

        Set<String> newWords = new HashSet<>();//text.words().stream().filter(w -> ! vocabulary.containsKey(w)).collect(toSet());
        Set<WordCell> wordsToSaveInItem = new HashSet<>();
        Filter filter = Filter.instance(dictionary.getLearningLanguage(), dictionary.getLevel());
        text.words().forEach(w->{
            if(vocabulary.containsKey(w))
                wordsToSaveInItem.add(vocabulary.get(w));
            else if(filter.execute(w)) {
                newWords.add(w);
                wordsToSaveInItem.add(generateNewWordCell(w, item, dictionary));
            }
        });
        //item.setWords(newWords.stream().map(w -> generateNewWordCell(w, item, dictionary)).collect(toSet()));
        item.setWords(wordsToSaveInItem);
        Translator translator = translatorManager.translator(dictionary.getLearningLanguage().toString(), dictionary.getNativeLanguage().toString());
        translator.translateNewWords(newWords.stream().collect(toSet()));
    }


    public Category category(@NotNull WordCell wordCell, @NotNull Dictionary dictionary) {
        return Category.NEW_WORD;
    }

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
        Text text = HashSet::new;
        try {
            if(item.getContent() != null)
                text = ParserProducer.getParser(item).parse(item.getContent());
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return text;
    }

    private WordCell generateNewWordCell(String word, MediaItem item, Dictionary dictionary) {
        WordCell wordCell = new WordCell(word);
        wordCell.setCategory(category(wordCell, dictionary));
        //wordCell.setMediaItems(new HashSet<MediaItem>(Arrays.asList(item)));
        return wordCell;
    }

}

