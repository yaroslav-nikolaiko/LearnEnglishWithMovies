package learn.english.core.text.processor;

import learn.english.core.entity.MediaItem;
import learn.english.core.entity.media.Movie;
import learn.english.core.entity.media.TVShow;
import learn.english.parser.Parser;
import learn.english.parser.subtitles.timeTextObjectImpl.SubtitlesParserTimeTextObjectImpl;
import learn.english.translator.Translator;

import javax.validation.constraints.NotNull;

/**
 * Created by yaroslav on 8/6/14.
 */
public class TextProcessorFactory {
    public static TextProcessor generate(@NotNull MediaItem item){
        Parser parser = null;
        Translator translator = null;
        if(item.getClass().equals(TVShow.class) || item.getClass().equals(Movie.class)){
            parser = new SubtitlesParserTimeTextObjectImpl();
        }

        return new TextProcessor(parser, translator);
    }


}
