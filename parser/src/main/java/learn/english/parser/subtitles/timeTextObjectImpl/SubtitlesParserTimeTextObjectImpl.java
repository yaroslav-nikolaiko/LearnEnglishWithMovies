package learn.english.parser.subtitles.timeTextObjectImpl;


import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.timeTextObjectImpl.subtitleFile.*;
import learn.english.parser.subtitles.SubtitlesParser;
import learn.english.parser.subtitles.bridge.Subtitles;
import learn.english.parser.subtitles.bridge.SubtitlesUnit;
import learn.english.parser.subtitles.timeTextObjectImpl.subtitleFile.TimedTextFileFormat;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by yaroslav on 5/14/14.
 */
public class SubtitlesParserTimeTextObjectImpl implements SubtitlesParser {
    private TimedTextObject tto;
    private String filename;
    private String format = "srt";

    private TimedTextFileFormat ttff;

    public SubtitlesParserTimeTextObjectImpl(String filename) {
        this.filename = filename;
        initTTFFormat();
        parse1();
    }

    public SubtitlesParserTimeTextObjectImpl(byte[] content){
        initTTFFormat();
        parse2(content);
    }

    public SubtitlesParserTimeTextObjectImpl() {
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

    protected void parse1(){
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

    protected void parse2(byte[] bytes){
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(bytes);
            tto = ttff.parseFile("null", is);
        } catch (FileNotFoundException | FatalParsingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Subtitles parse() throws ParserException {
        NavigableMap<Integer, SubtitlesUnit> map = new TreeMap<>();
        for (Map.Entry<Integer, Caption> entry : tto.captions.entrySet())
            map.put(entry.getKey(), new SubtitlesUnit(entry.getValue().content));

        return new Subtitles(map);
    }

    @Override
    public Subtitles parse(byte[] content) throws ParserException {
        initTTFFormat();
        parse2(content);

        NavigableMap<Integer, SubtitlesUnit> map = new TreeMap<>();
        for (Map.Entry<Integer, Caption> entry : tto.captions.entrySet())
            map.put(entry.getKey(), new SubtitlesUnit(entry.getValue().content));

        return new Subtitles(map);
    }
}
