package learn.english.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 9/23/14.
 */
@XmlRootElement(name = "DicResult")
@XmlAccessorType(XmlAccessType.FIELD)
public @Data class WordInfo {
    @XmlElement(name="def")
    List<Definition> definitions = new ArrayList<>();

    public String summary() {
        String actualWord = definitions.stream().findAny().orElse(new Definition("")).getText();
        return "<FONT COLOR=\"#a298fa\">"+actualWord+"</FONT>"+" -->  "+shortSummary();
    }

    public String shortSummary(){
        Set<String> result = definitions.stream()
                .flatMap(d -> d.getTranslations().stream())
                .flatMap(t -> {
                    List<Text> synonymsText = t.getSynonyms();
                    Set<String> synonyms = synonymsText.stream().map(Text::getText).collect(toSet());
                    synonyms.add(t.getText());
                    return synonyms.stream();
                })
                .collect(toSet());

        return "<FONT COLOR=\"#a9815d\">"+result.toString().replace("[","").replace("]","")+"</FONT>";
    }


    @XmlAccessorType(XmlAccessType.FIELD) @NoArgsConstructor
    public @Data static class Definition{
        String text;
        @XmlAttribute(name = "pos")
        String partOfSpeech;
        @XmlElement(name="tr")
        List<Translation> translations = new ArrayList<>();

        public Definition(String text){
            this.text = text;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public @Data static class Translation{
        String text;
        @XmlAttribute(name = "pos")
        String partOfSpeech;
        @XmlElement(name="syn")
        List<Text> synonyms = new ArrayList<>();
        List<Text> mean = new ArrayList<>();
        @XmlElement(name="ex")
        List<Examples> examples = new ArrayList<>();
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
