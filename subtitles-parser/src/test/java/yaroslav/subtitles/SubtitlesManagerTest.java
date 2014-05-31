package yaroslav.subtitles;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import yaroslav.subtitles.bridge.Subtitles;
import yaroslav.subtitles.exception.SubtitlesParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesManagerTest {

    @Test
    public void getSubtitlesTest() throws IOException, SubtitlesParserException {
        String filename = "src/test/resources/small_file.srt";
        SubtitlesManager manager = new SubtitlesManager();
        Subtitles subtitles = manager.getSubtitles(readFile(filename));
        System.out.println(subtitles.getAllWords());
    }

    private byte[] readFile(String filename) throws IOException {
        String encoding = String.valueOf(StandardCharsets.UTF_8);
        FileInputStream is = new FileInputStream(filename);
        return IOUtils.toByteArray(is);
    }
}
