package learn.english.core;

import org.apache.commons.io.IOUtils;
import learn.english.core.entity.User;
import learn.english.parser.subtitles.SubtitlesManager;
import learn.english.parser.subtitles.bridge.Subtitles;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by yaroslav on 6/1/14.
 */
public class Main {
    public static void main(String[] args) throws Exception{
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
//        items.addToDataBase(item);
//        dictionary.setMediaItems(items);
//
//        List<Dictionary> dictionaries = new ArrayList<>();
//        dictionaries.addToDataBase(dictionary);
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
