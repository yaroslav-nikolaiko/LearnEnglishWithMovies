package learn.english.vlc;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by yaroslav on 9/25/14.
 */
@Data @NoArgsConstructor
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class VlcStatusData {
    private static final String META = "meta";
    Integer time;
    String state;
    Information information;

    public VlcStatusData(Integer time){
        this.time = time;
    }

    public Category getMetaInfo(){
        for (Category category : information.getCategories())
            if(category.getName().equals(META))
                return category;
        return null;
    }

    @XmlAccessorType(XmlAccessType.FIELD) @NoArgsConstructor
    static @Data public class Information{
        @XmlElement(name = "category")
        List<Category> categories;
    }

    @XmlAccessorType(XmlAccessType.FIELD) @NoArgsConstructor
    static @Data public class Category{
        @XmlAttribute
        String name;
        @XmlElement(name = "info")
        List<Item> infos;
    }

    @XmlAccessorType(XmlAccessType.FIELD) @NoArgsConstructor
    static @Data  public class Item{
        @XmlAttribute
        String name;
        @XmlValue
        String value;
    }
}
