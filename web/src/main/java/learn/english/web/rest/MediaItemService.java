package learn.english.web.rest;

import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.model.entity.wraper.WordCells;
import learn.english.model.utils.MediaItemType;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.core.GenericEntity;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 9/6/14.
 */
public class MediaItemService implements Serializable{
    @Inject RestService restService;

    public MediaItem generateItem(MediaItemType type) {
        return restService.path("item/type").path(type.toString()).get(MediaItem.class);
    }

    public Collection<WordCell> getUniqueWords(MediaItem mediaItem) {
        return  restService.path("wordcell/unique").path(String.valueOf(mediaItem.getId())).get(WordCells.class).getWordCells();
    }

    public Collection<WordCell> getWords(List<MediaItem> mediaItems) {
        List<Long> iDs = mediaItems.stream().map(MediaItem::getId).collect(toList());
        restService.path("wordcell");
        for (Long iD : iDs)
            restService.param("miID", String.valueOf(iD));

        return restService.get(WordCells.class).getWordCells();
    }
}
