package learn.english.web.controller;

import learn.english.core.entity.MediaItem;
import learn.english.core.entity.WordCell;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.utils.Category;
import learn.english.web.interceptor.ValidationHandler;
import lombok.Data;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
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

    //Set<WordCell> words;
    Map<String, WordCell> words;
    Category leftCategory = Category.LEARNED;
    Category rightCategory = Category.NEW_WORD;

    DualListModel<WordCell> dualList = new DualListModel<>(new ArrayList<WordCell>(), new ArrayList<WordCell>());
    //List<WordCell> cache = new ArrayList<>();

    public void update(){
        List<MediaItem> selectedItems = sessionController.getSelectedMediaItems();
        words = selectedItems.stream().flatMap(item -> item.getWords().stream()).
                distinct().collect(toMap(WordCell::getWord, cell -> cell));
        updateDualList();
    }

    void updateDualList() {
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

    public void onTransfer(TransferEvent event) throws EJBIllegalArgumentException{
        //isAdd() - is transfer from source to target  (left->right)
        //isRemove() - is transfer form target to source (right->left)
        for (Object word : event.getItems()) {
            WordCell wordCell = (WordCell)word;
            words.get(wordCell.getWord()).setCategory(event.isAdd() ? rightCategory:leftCategory);
        }
        sessionController.updateDictionary();
    }

    public void submit()throws EJBIllegalArgumentException{
        sessionController.updateDictionary() ;
    }

    @Produces
    public Map<String, WordCell>  getWords() {
        return words != null ? words : new HashMap<>();
    }

/*    public void onTransfer(TransferEvent event) {
        event.getSource()

    }*/
}
