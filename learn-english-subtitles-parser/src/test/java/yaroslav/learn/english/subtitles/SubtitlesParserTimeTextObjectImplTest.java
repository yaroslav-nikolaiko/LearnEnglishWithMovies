package yaroslav.learn.english.subtitles;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import yaroslav.learn.english.subtitles.SubtitlesParserTimeTextObjectImpl;
import yaroslav.learn.english.subtitles.bridge.Subtitles;
import yaroslav.learn.english.subtitles.exception.SubtitlesParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesParserTimeTextObjectImplTest {

    @Test
    @Ignore
    public void subtitlesParserTimeTextObjectTest() throws SubtitlesParserException {
        String filename = "small_file.srt";
        SubtitlesParserTimeTextObjectImpl parser = new SubtitlesParserTimeTextObjectImpl(filename);
        Subtitles subtitles = parser.parse();
        System.out.println(subtitles.getAllWords());
    }

    @Test
    @Ignore
    public void subtitlesParserTimeTextObjectByteArrayTestTest() throws SubtitlesParserException, IOException {
        String filename = "small_file.srt";
        byte[] content = readFile(filename);
        SubtitlesParserTimeTextObjectImpl parser = new SubtitlesParserTimeTextObjectImpl();
        Subtitles subtitles = parser.parse(content);
        System.out.println(subtitles.getAllWords());
    }

    private byte[] readFile(String filename) throws IOException {
        String encoding = String.valueOf(StandardCharsets.UTF_8);
        FileInputStream is = new FileInputStream(filename);
        return IOUtils.toByteArray(is);
    }

}
