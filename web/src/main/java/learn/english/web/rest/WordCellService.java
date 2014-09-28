package learn.english.web.rest;

import learn.english.model.entity.WordCell;
import learn.english.model.dto.WordInfo;
import learn.english.model.dto.WordCells;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yaroslav on 9/6/14.
 */
public class WordCellService implements Serializable {
    @Inject
    RestService restService;

    public String translate(String text, String languageFrom, String languageTo) {
        return restService.path("translator").path(text).param("from", languageFrom).param("to", languageTo).get(String.class);
    }

    public void update(WordCell wordCell){
        restService.path("wordcell").update(wordCell);
    }

    public void update(List<WordCell> wordCells){
        restService.path("wordcell/list").update(new WordCells(wordCells));
    }

    public WordInfo advanceTranslate(String word,String languageFrom, String languageTo ){
        return restService.path("translator/advance").path(word).param("from", languageFrom).param("to", languageTo).get(WordInfo.class);
    }

}
