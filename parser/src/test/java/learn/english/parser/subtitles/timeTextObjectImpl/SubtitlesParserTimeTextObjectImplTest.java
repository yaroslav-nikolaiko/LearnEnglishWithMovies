package learn.english.parser.subtitles.timeTextObjectImpl;

import learn.english.parser.subtitles.timeTextObjectImpl.SubtitlesParserTimeTextObjectImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.bridge.Subtitles;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesParserTimeTextObjectImplTest {

    @Test
    @Ignore
    public void subtitlesParserTimeTextObjectTest() throws ParserException {
        String filename = "small_file.srt";
        SubtitlesParserTimeTextObjectImpl parser = new SubtitlesParserTimeTextObjectImpl(filename);
        Subtitles subtitles = parser.parse();
        System.out.println(subtitles.words());
    }

    @Test
    @Ignore
    public void subtitlesParserTimeTextObjectByteArrayTestTest() throws ParserException, IOException {
        String filename = "small_file.srt";
        byte[] content = readFile(filename);
        SubtitlesParserTimeTextObjectImpl parser = new SubtitlesParserTimeTextObjectImpl();
        Subtitles subtitles = parser.parse(content);
        System.out.println(subtitles.words());
    }

    private byte[] readFile(String filename) throws IOException {
        String encoding = String.valueOf(StandardCharsets.UTF_8);
        FileInputStream is = new FileInputStream(filename);
        return IOUtils.toByteArray(is);
    }

}
