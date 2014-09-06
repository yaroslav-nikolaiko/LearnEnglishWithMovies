package learn.english.web.rest;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yaroslav on 9/6/14.
 */
public class DictionaryService implements Serializable {
    @Inject
    RestService restService;

    public void addMediaItem(Dictionary currentDictionary, MediaItem item) {
        Response response = restService.path("item").param("dictionaryID", currentDictionary.getId()).post(item);
        Long itemID = restService.entityId(response);
        MediaItem persistedItem = restService.path("item").path(String.valueOf(itemID)).get(MediaItem.class);
        currentDictionary.addMediaItem(persistedItem);
    }

    public void removeMediaItems(Dictionary currentDictionary, List<MediaItem> selectedMediaItems) {
        for (MediaItem item : selectedMediaItems) {
            restService.path("item").param("dictionaryID", currentDictionary.getId()).delete(item.getId());
            currentDictionary.removeMediaItem(item);
        }
    }

    public void update(Dictionary currentDictionary) {
        restService.path("dict").update(currentDictionary);
    }
}
