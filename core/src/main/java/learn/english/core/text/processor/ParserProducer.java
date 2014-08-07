package learn.english.core.text.processor;

import learn.english.core.entity.MediaItem;
import learn.english.core.entity.media.Movie;
import learn.english.core.entity.media.TVShow;
import learn.english.parser.Parser;
import learn.english.parser.Text;
import learn.english.parser.subtitles.SubtitlesParser;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.HashSet;


/**
 * Created by yaroslav on 8/7/14.
 */
public class ParserProducer {
    //@Produces
/*    public static Parser getParser(InjectionPoint ip){
        Class<?> itemClass = ip.getBean().getBeanClass();
        if(itemClass.equals(TVShow.class) || itemClass.equals(Movie.class))
            return SubtitlesParser.defaultParser();

        //Text emptyText  =  HashSet::new;
        return (a) -> HashSet::new;
    }*/

    public static Parser getParser(MediaItem item){
        Class<?> itemClass = item.getClass();
        if(itemClass.equals(TVShow.class) || itemClass.equals(Movie.class))
            return SubtitlesParser.defaultParser();

        //Text emptyText  =  HashSet::new;
        return (a) -> HashSet::new;
    }
}
