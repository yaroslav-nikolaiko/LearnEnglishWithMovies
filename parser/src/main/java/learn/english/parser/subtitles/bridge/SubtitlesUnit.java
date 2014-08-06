package learn.english.parser.subtitles.bridge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaroslav on 5/31/14.
 */
@Data @EqualsAndHashCode(of={"startTime"})
public  class SubtitlesUnit implements Comparable<SubtitlesUnit>{
    private String fullText;
    /**
     * Text with removed html tags (<br/> etc.)
     */
    private String text;
    /**
     * Larger then 1 character words, extracted from text with removed html tags, separated by whitespaces
     */
    private List<String> words;
    private Integer startTime;

    public SubtitlesUnit(String fullText) {
        this.fullText = fullText;
        this.text = removeHtmlTags(fullText);
        words = split(this.text);
    }

    public SubtitlesUnit(String fullText, Integer startTime) {
        this(fullText);
        this.startTime = startTime;
    }

    String removeHtmlTags(String sample){
        return sample.replaceAll("\\<[^>]*>"," ");
    }

    List<String> split(String sample){
        Pattern p = Pattern.compile("[\\w']+");
        Matcher m = p.matcher(sample);

        List<String> result = new ArrayList<>();
        while ( m.find() ){
            String temp = sample.substring(m.start(), m.end());
            if(temp.length() > 1)
                result.add(temp);
        }
        return result;
    }

    @Override
    public int compareTo(SubtitlesUnit o) {
        return startTime - o.startTime;
    }
}
