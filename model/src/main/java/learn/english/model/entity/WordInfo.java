package learn.english.model.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by yaroslav on 9/23/14.
 */
@XmlRootElement(name = "DicResult")
@XmlAccessorType(XmlAccessType.FIELD)
public @Data class WordInfo {
    @XmlElement(name="def")
    List<Definition> definitions;

    @XmlAccessorType(XmlAccessType.FIELD)
    public @Data static class Definition{
        String text;
        @XmlAttribute(name = "pos")
        String partOfSpeech;
        @XmlElement(name="tr")
        List<Translation> translations;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public @Data static class Translation{
        String text;
        @XmlAttribute(name = "pos")
        String partOfSpeech;
        @XmlElement(name="syn")
        List<Text> synonyms;
        List<Text> mean;
        @XmlElement(name="ex")
        List<Examples> examples;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public @Data static class Examples {
        String text;
        @XmlElement(name="tr")
        Text translation;
    }

    public @Data static class Text {
        String text;
    }
}
