package yaroslav.learn.english.subtitles.bridge;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesUnitTest {
    SubtitlesUnit unit;

    @Before
    public void init(){
        unit = new SubtitlesUnit("some text");
    }

    @Test
    public void removeHtmlTagsTest(){
        String text = "<i>— ♪ — That Lord of Castamere — ♪ —</i>"+ "\n";
        text+= "And now the rains... — ♪ —</i> and some more text";

        String expected = " — ♪ — That Lord of Castamere — ♪ — "+ "\n";
        expected+= "And now the rains... — ♪ —  and some more text";

        assertEquals(expected, unit.removeHtmlTags(text));
    }

    @Test
    public void splitTest(){
        String text = "With the Tyrells, no"+ "\n";
        text+= "will — ♪ century.";

        List<String> result = unit.split(text);
        assertEquals("With", result.get(0));
        assertEquals("the", result.get(1));
        assertEquals("Tyrells", result.get(2));
        assertEquals("no", result.get(3));
        assertEquals("will", result.get(4));
        assertEquals("century", result.get(5));
        assertEquals(6, result.size());
    }

}
