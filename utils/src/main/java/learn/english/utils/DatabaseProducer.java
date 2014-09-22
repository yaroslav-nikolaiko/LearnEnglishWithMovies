package learn.english.utils;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.UnknownHostException;

/**
 * Created by yaroslav on 6/12/14.
 */
public class DatabaseProducer {
    @Produces
    @PersistenceContext(unitName = "LearnEnglishWithMovies")
    private EntityManager em;

    @Produces
    DB mongoProducer(){
        String host = System.getenv("MONGODB_HOST");
        Integer port = Integer.valueOf(System.getenv("MONGODB_PORT"));
        try {
            MongoClient mongo = new MongoClient(host, port);
            return mongo.getDB("lingvo-movie");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Produces @TranslatorDB
    DB mongoProducerTranslator(){
        String host = System.getenv("MONGODB_HOST");
        Integer port = Integer.valueOf(System.getenv("MONGODB_PORT"));
        try {
            MongoClient mongo = new MongoClient(host, port);
            return mongo.getDB("lingvo-movie-translator");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
