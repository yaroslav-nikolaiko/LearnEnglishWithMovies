package learn.english.translator.core;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by yaroslav on 8/10/14.
 */
@XmlRootElement(name = "Translation")
@XmlAccessorType(XmlAccessType.FIELD)
public @Data class YandexXMLObject {
    @XmlAttribute
    Integer code;
    @XmlAttribute(name = "lang")
    String language;
    @XmlElement
    List<String> text;
}
