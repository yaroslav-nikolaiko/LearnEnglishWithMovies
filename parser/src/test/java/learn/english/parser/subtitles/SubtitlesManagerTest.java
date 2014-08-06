package learn.english.parser.subtitles;

import learn.english.parser.subtitles.SubtitlesManager;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.bridge.Subtitles;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesManagerTest {

    @Test
    public void getSubtitlesTest() throws IOException, ParserException {
        String filename = "src/test/resources/small_file.srt";
        SubtitlesManager manager = new SubtitlesManager();
        Subtitles subtitles = manager.getSubtitles(readFile(filename));
        System.out.println(subtitles.words());
    }

    private byte[] readFile(String filename) throws IOException {
        String encoding = String.valueOf(StandardCharsets.UTF_8);
        FileInputStream is = new FileInputStream(filename);
        return IOUtils.toByteArray(is);
    }
}
