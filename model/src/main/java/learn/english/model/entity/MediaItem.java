package learn.english.model.entity;

import learn.english.model.entity.media.Book;
import learn.english.model.entity.media.Movie;
import learn.english.model.entity.media.Song;
import learn.english.model.entity.media.TVShow;
import learn.english.model.utils.Persistent;
import lombok.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "MediaItem", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "DICTIONARY_ID"}))
@Access(AccessType.FIELD)
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name"})
@XmlSeeAlso({Book.class, Movie.class, Song.class, TVShow.class})
@XmlDiscriminatorNode("@classifier")
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "wordCell_mediaItem", joinColumns = @JoinColumn(name = "MEDIAITEM_ID"),
                                            inverseJoinColumns = @JoinColumn(name = "WORDCELL_ID"))
    private Set<WordCell> words;

    public MediaItem() {
        words = new HashSet<>();
    }

    public boolean contains(@NotNull WordCell word) {
        return words.contains(word);
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
