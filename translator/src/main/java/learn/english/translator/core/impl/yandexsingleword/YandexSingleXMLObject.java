package learn.english.translator.core.impl.yandexsingleword;

import learn.english.translator.Translator;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by yaroslav on 9/22/14.
 */
@XmlRootElement(name = "DicResult")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class YandexSingleXMLObject {
    @XmlElement
    List<Entry> entries;


    @XmlRootElement(name = "def")
    @XmlAccessorType(XmlAccessType.FIELD) @Data
    public static class Entry{
        String text;
        @XmlAttribute
        String pos; //part of speech
        @XmlElement
        List<Translation> translation;

    }

    @XmlRootElement(name = "tr")
    @XmlAccessorType(XmlAccessType.FIELD) @Data
    public static class Translation{
        String text;
        @XmlAttribute
        String pos; //part of speech
        @XmlElement
        List<Synonyms> synonyms;
        @XmlElement
        List<Mean> means;
        @XmlElement
        List<Example> examples;
    }

    @XmlRootElement(name = "syn")
    @XmlAccessorType(XmlAccessType.FIELD) @Data
    public static class Synonyms {
        String text;
    }

    @XmlRootElement(name = "mean")
    @XmlAccessorType(XmlAccessType.FIELD) @Data
    public static class Mean{
        String text;
    }

    @XmlRootElement(name = "ex")
    @XmlAccessorType(XmlAccessType.FIELD) @Data
    public static class Example{
        String text;
        @XmlElement
        List<Translation> translation;
    }
}


