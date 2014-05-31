package yaroslav;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by yaroslav on 5/31/14.
 */
@XmlRootElement(name = "Translation")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlObject {
    @XmlAttribute
    private Integer code;
    @XmlAttribute(name = "lang")
    private String language;
    @XmlElement
    private List<String> text;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Code --> " + code + "\n" + "Language --> " + language + "\n" + "Text --> " + text + "\n";
    }
}
