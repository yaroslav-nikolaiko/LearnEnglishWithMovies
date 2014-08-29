package learn.english.core.logger.message;

import learn.english.core.entity.MediaItem;
import learn.english.core.entity.User;
import learn.english.core.entity.WordCell;
import org.apache.logging.log4j.message.Message;

import java.util.Map;
import java.util.Set;

/**
 * Created by yaroslav on 8/29/14.
 */
public class MediaItemMessage extends CRUDMessage {

    public static MediaItemMessage computeWordCells(MediaItem item, Map<String, WordCell> vocabulary){
        MediaItemMessage message = new MediaItemMessage();
        message.text.append(String.format("Compute Word Cells for %s\n",item));
        message.text.append(String.format("------------- Vocabulary initial size = %s\n",vocabulary.size()));
        return message;
    }

    public MediaItemMessage setFinalVocabulary(Map<String, WordCell> vocabulary){
        text.append(String.format("------------- Vocabulary final size = %s\n",vocabulary.size()));
        return this;
    }

    public MediaItemMessage setNewWords(Set<String> newWords){
        text.append(String.format("------------- New Words Set size = %s\n",newWords.size()));
        return this;
    }

}
