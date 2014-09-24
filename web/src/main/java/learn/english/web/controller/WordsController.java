package learn.english.web.controller;

import learn.english.model.entity.*;
import learn.english.model.entity.Dictionary;
import learn.english.web.rest.MediaItemService;
import learn.english.model.utils.Category;
import learn.english.web.rest.WordCellService;
import learn.english.web.validation.ValidationHandler;
import lombok.Data;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 * Created by yaroslav on 8/9/14.
 */
@Named
@SessionScoped
@ValidationHandler
public @Data class WordsController implements Serializable {
    @Inject
    SessionController sessionController;
    @Inject
    WordCellService wordCellService;
    //@EJB TranslatorManager translatorManager;
    @Inject MediaItemService mediaItemService;

    private boolean unique;
    String currentWord;

    Map<String, WordCell> words;
    Category leftCategory = Category.KNOWN;
    Category rightCategory = Category.NEW_WORD;

    boolean leftTranslation;
    boolean rightTranslation;

    boolean mouseOver;

    DualListModel<WordCell> dualList = new DualListModel<>(new ArrayList<WordCell>(), new ArrayList<WordCell>());

    public void update(){
        List<MediaItem> selectedItems = sessionController.getSelectedMediaItems();
        if(unique && selectedItems.size()==1){
            words = mediaItemService.getUniqueWords(selectedItems.get(0)).stream().
                    collect(toMap(WordCell::getWord, cell -> cell));
            updateDualList();
        }else{
/*            words = selectedItems.stream().flatMap(item -> item.getWords().stream()).
                    distinct().collect(toMap(WordCell::getWord, cell -> cell));*/
            words = mediaItemService.getWords(selectedItems).stream().collect(toMap(WordCell::getWord, cell -> cell));
            updateDualList();
        }
    }

    public void updateDualList() {
        List<WordCell> sourceLeftList = new ArrayList<>();
        List<WordCell> targetRightList = new ArrayList<>();
        words.values().stream().forEach(w -> {
            if (w.getCategory() == leftCategory)
                sourceLeftList.add(w);
            else if (w.getCategory() == rightCategory)
                targetRightList.add(w);
        });
        dualList.setSource(sourceLeftList);
        dualList.setTarget(targetRightList);
    }

    public void onTransfer(TransferEvent event) {
        List<WordCell> wordCellsToUpdate = new ArrayList<>();
        for (Object word : event.getItems()) {
            WordCell wordCell = words.get(((WordCell) word).getWord());
            wordCell.setCategory(event.isAdd() ? rightCategory : leftCategory);
            wordCellsToUpdate.add(wordCell);
        }
        updateWordCells(wordCellsToUpdate);
        //sessionController.updateDictionary();

    }


    public void updateWordCells(List<WordCell> wordCells) {
        wordCellService.update(wordCells);
        //sessionController.setSelectedMediaItems(null);
        //logger.debug(DictionaryMessage.update(currentDictionary));
    }



    public WordInfo advanceTranslate() {
        if(currentWord==null || currentWord.isEmpty())
            return null;
        Dictionary dictionary = sessionController.getCurrentDictionary();
        String from = dictionary.getLearningLanguage().toString();
        String to = dictionary.getNativeLanguage().toString();
        return wordCellService.advanceTranslate(currentWord, from, to);
    }

    public void advanceTranslationListener(){
        String word = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("word");
        currentWord = word;
    }

    public void onMouseOver(){
        mouseOver = true;
    }

    public void onMouseOut(){
        mouseOver = false;
    }



    @Produces
    public Map<String, WordCell>  getWords() {
        return words != null ? words : new HashMap<>();
    }






    /*
    public String translate(String word){
*/
/*        if(! mouseOver)
            return "";*//*

        Dictionary d = sessionController.getCurrentDictionary();
        return wordCellService.translate(word,d.getLearningLanguage().toString(), d.getNativeLanguage().toString());
    }

    public boolean isTranslate(WordCell word){
        if( leftTranslation==rightTranslation )
            return leftTranslation;
        boolean contains;
        if(leftTranslation) {
            contains = dualList.getSource().contains(word);
            if(contains)
                return true;
            else{
                if(rightTranslation){
                    return dualList.getTarget().contains(word);
                }else
                    return false;
            }
        }
        if( rightTranslation){
            return dualList.getTarget().contains(word);
        }
        return false;
    }
*/

}
