package learn.english.translator.core;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import learn.english.translator.Translator;
import learn.english.utils.TranslatorDB;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;*/

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Properties;

/**
 * Created by yaroslav on 9/21/14.
 */
@Singleton
public class TranslatorManagerMongo {
    @Inject @TranslatorDB
    DB db;

    String dataBaseName(String languageFrom, String languageTo){
        return languageFrom+"-"+languageTo;
    }

    public Translator translator(String languageFrom, String languageTo) {
        String dataBaseName = dataBaseName(languageFrom, languageTo);
        DBCollection table = db.getCollection(dataBaseName(languageFrom,languageTo));
        return new GoogleTranslator(languageFrom, languageTo, table);
    }
}
