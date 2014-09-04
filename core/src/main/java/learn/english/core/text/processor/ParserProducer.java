package learn.english.core.text.processor;


import learn.english.model.entity.MediaItem;
import learn.english.model.entity.media.Movie;
import learn.english.model.entity.media.TVShow;
import learn.english.parser.Parser;
import learn.english.parser.subtitles.SubtitlesParser;


import java.util.HashSet;


/**
 * Created by yaroslav on 8/7/14.
 */
public class ParserProducer {
    public static Parser getParser(MediaItem item){
        Class<?> itemClass = item.getClass();
        if(itemClass.equals(TVShow.class) || itemClass.equals(Movie.class))
            return SubtitlesParser.defaultParser();

        //Text emptyText  =  HashSet::new;
        return (a) -> HashSet::new;
    }
}
