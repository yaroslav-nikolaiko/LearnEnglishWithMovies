package yaroslav;

import org.apache.commons.io.IOUtils;
import yaroslav.entity.Dictionary;
import yaroslav.entity.User;
import yaroslav.entity.media.MediaItem;
import yaroslav.entity.media.TVShow;
import yaroslav.subtitles.SubtitlesManager;
import yaroslav.subtitles.bridge.Subtitles;
import yaroslav.subtitles.exception.SubtitlesParserException;
import yaroslav.util.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yaroslav on 6/1/14.
 */
public class Main {
    public static void main(String[] args) throws SubtitlesParserException, IOException {
        System.out.println("ping");

        byte[] content = readFile();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LearnEnglishWithMovies");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        SubtitlesManager subtitlesManager = new SubtitlesManager();
        Subtitles subtitles = subtitlesManager.getSubtitles(content);



        User user = new User();
        Dictionary dictionary = new Dictionary();
        TVShow item = new TVShow();
        item.setDictionary(dictionary);
        item.setContent(content);


        dictionary.setLearningLanguage("en");
        dictionary.setLevel(Level.INTERMEDIATE);
        dictionary.setNativeLanguage("ru");

        dictionary.addWords(subtitles.getAllWords());
        List<MediaItem> items = new ArrayList<>();
        items.add(item);
        dictionary.setMediaItems(items);

        List<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.add(dictionary);

        user.setDictionaries(dictionaries);

        user.setName("yaroslav");
        user.setEmail("rachmaninov@bigmir.net");
        user.setPassword("admin");


        tx.begin();
        em.persist(user);
        em.persist(dictionary);
        em.persist(item);
        tx.commit();


    }

    private static byte[] readFile() throws IOException {
        String filepath = "core/src/main/resources/small_file.srt";
        FileInputStream is = new FileInputStream(filepath);
        return IOUtils.toByteArray(is);
    }

}
