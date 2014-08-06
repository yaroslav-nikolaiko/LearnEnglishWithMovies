package learn.english.core.entity;

import lombok.*;
import org.apache.commons.beanutils.PropertyUtils;
import learn.english.core.utils.Persistent;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "MediaItem", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "DICTIONARY_ID"}))
@Access(AccessType.FIELD)
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name", "dictionary"})
public abstract class MediaItem implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] content;
    private String filename;
    @ManyToOne
    private Dictionary dictionary;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wordCell_mediaItem", joinColumns = @JoinColumn(name = "MEDIAITEM_ID"),
                                            inverseJoinColumns = @JoinColumn(name = "WORDCELL_ID"))
    private List<WordCell> words;

    public MediaItem() {
        words = new ArrayList<>();
    }

    public void addWords(Collection<String> words){
        for(String word : words){
            this.words.add(new WordCell(word));
        }
    }

    public Collection<Attribute> getAttributes() {
        List<Attribute> list = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields())
            if ( ! "serialVersionUID".equals(field.getName()))
                list.add(new AttributeReflection(this, field.getName()));
        return list;
    }



    public static interface Attribute{
        String getName();
        Object getValue();
        void setValue(Object value);
    }

    public @AllArgsConstructor() static class AttributeReflection implements Attribute{
        private MediaItem holder;
        private @Getter String name;

        public Object getValue(){
            try {
                return PropertyUtils.getProperty(holder, name);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void setValue(Object value){
            try {
                if (value != null)
                    PropertyUtils.setProperty(holder, name, value);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

}
