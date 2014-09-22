package learn.english.translator.core.dao.factory.impl;

import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.dao.factory.TranslatorDAOFactory;
import learn.english.translator.core.dao.impl.TranslatorDAOFileCache;
import learn.english.translator.qualifier.storage.FileCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaroslav on 9/22/14.
 */
@FileCache
public class TranslatorDAOFactoryFileCache implements TranslatorDAOFactory {
    Map<String, TranslatorDAO> pool = new ConcurrentHashMap<>();
    @Override
    public TranslatorDAO getInstance(String DBName) {
        TranslatorDAO dao = pool.get(DBName);
        if(dao==null){
            dao = new TranslatorDAOFileCache(DBName);
            pool.put(DBName, dao);
        }
        return dao;
    }

    @Override
    public void onDestroy(){
        for (TranslatorDAO dao : pool.values()) {
            dao.onDestroy();
        }
    }

/*    public boolean contains(String DBName, Map<String, TranslatorDAO> pool) {
        return pool.containsKey(DBName);
    }

    public void create(String DBName,TranslatorDAO dao, Map<String, TranslatorDAO> pool) {
        pool.put(DBName, dao);
    }*/
}
