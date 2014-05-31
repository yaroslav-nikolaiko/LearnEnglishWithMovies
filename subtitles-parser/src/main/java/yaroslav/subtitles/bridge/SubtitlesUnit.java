package yaroslav.subtitles.bridge;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesUnit implements Comparable<SubtitlesUnit>{
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

    public String getFullText() {
        return fullText;
    }

    public String getText() {
        return text;
    }

    public List<String> getWords() {
        return words;
    }

    public Integer getStartTime() {
        return startTime;
    }

    String removeHtmlTags(String sample){
        return sample.replaceAll("\\<[^>]*>"," ");
    }

    List<String> split(String sample){
        Pattern p = Pattern.compile("[\\w']+");
        Matcher m = p.matcher(sample);

        List<String> result = new ArrayList<>();
        while ( m.find() )
            result.add(sample.substring(m.start(), m.end()));
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubtitlesUnit that = (SubtitlesUnit) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return startTime != null ? startTime.hashCode() : 0;
    }


    @Override
    public int compareTo(SubtitlesUnit o) {
        return startTime - o.startTime;
    }
}
