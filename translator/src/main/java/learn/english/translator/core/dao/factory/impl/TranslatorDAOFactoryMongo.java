package learn.english.translator.core.dao.factory.impl;

import com.mongodb.DB;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.dao.factory.TranslatorDAOFactory;
import learn.english.translator.core.dao.impl.TranslatorDAOMongo;
import learn.english.translator.qualifier.storage.MongoDB;
import learn.english.utils.TranslatorDB;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaroslav on 9/22/14.
 */
@MongoDB
public class TranslatorDAOFactoryMongo implements TranslatorDAOFactory {
    Map<String, TranslatorDAO> pool = new ConcurrentHashMap<>();
    @Inject @TranslatorDB
    DB db;

    @Override
    public TranslatorDAO getInstance(String DBName) {
        TranslatorDAO dao = pool.get(DBName);
        if(dao==null){
            dao = new TranslatorDAOMongo(DBName, db);
            pool.put(DBName, dao);
        }
        return dao;
    }
}
