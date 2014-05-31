package yaroslav.subtitles;

import yaroslav.subtitles.parser.subtitleFile.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yaroslav on 5/14/14.
 */
public class SubtitlesParser {
    private TimedTextObject tto;
    private String filename;
    private String format = "srt";

    private TimedTextFileFormat ttff;

    public SubtitlesParser(String filename) {
        this.filename = filename;
        initTTFFormat();
        parse();
    }

    public TimedTextObject getTto() {
        return tto;
    }

    protected void initTTFFormat(){
        switch (format){
            case "srt" : ttff = new FormatSRT();
                break;
            case "stl" : ttff = new FormatSRT();
                break;
            case "css" : ttff = new FormatSCC();
                break;
            case "xml" : ttff = new FormatTTML();
                break;
            case "ass" : ttff = new FormatASS();
                break;
            default: ttff = new FormatSRT();
        }
    }

    protected void parse(){
        InputStream is = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            is = classloader.getResourceAsStream(filename);
            tto = ttff.parseFile(filename, is);
        } catch (FileNotFoundException | FatalParsingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
