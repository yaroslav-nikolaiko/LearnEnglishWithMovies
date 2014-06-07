package yaroslav;

import org.apache.commons.io.IOUtils;
import yaroslav.entity.Dictionary;
import yaroslav.entity.User;
import yaroslav.entity.media.MediaItem;
import yaroslav.entity.media.TVShow;
import yaroslav.subtitles.SubtitlesManager;
import yaroslav.subtitles.bridge.Subtitles;
import yaroslav.subtitles.exception.SubtitlesParserException;
import yaroslav.util.Language;
import yaroslav.util.Level;

import javax.persistence.*;
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



//        User user = new User();
//        Dictionary dictionary = new Dictionary();
//        TVShow item = new TVShow();
//        item.setDictionary(dictionary);
//        item.setContent(content);
//
//
//        dictionary.setLearningLanguage(Language.en);
//        dictionary.setLevel(Level.INTERMEDIATE);
//        dictionary.setNativeLanguage(Language.ru);
//
//        item.addWords(subtitles.getAllWords());
//        List<MediaItem> items = new ArrayList<>();
//        items.add(item);
//        dictionary.setMediaItems(items);
//
//        List<Dictionary> dictionaries = new ArrayList<>();
//        dictionaries.add(dictionary);
//
//        user.setDictionaries(dictionaries);
//
//        user.setName("yaroslav");
//        user.setEmail("rachmaninov@bigmir.net");
//        user.setPassword("admin");
//
//
//        tx.begin();
//        em.persist(user);
//        em.persist(dictionary);
//        em.persist(item);
//        tx.commit();

        Query query = em.createQuery("SELECT u FROM User u WHERE u.name='yaroslav' ");
        User user = (User) query.getSingleResult();
        System.out.println(user.getName());


    }

    private static byte[] readFile() throws IOException {
        String filepath = "core/src/main/resources/small_file.srt";
        FileInputStream is = new FileInputStream(filepath);
        return IOUtils.toByteArray(is);
    }

}
