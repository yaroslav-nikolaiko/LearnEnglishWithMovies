package learn.english.translator.core.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import learn.english.translator.core.dao.TranslatorDAO;

/**
 * Created by yaroslav on 9/22/14.
 */
public class TranslatorDAOMongo implements TranslatorDAO {
    DBCollection table;
    String DBName;

    public TranslatorDAOMongo(String DBName, DB db) {
        this.DBName = DBName;
        table = db.getCollection(DBName);
    }
    @Override
    public boolean contains(String text) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("text", text);
        DBObject result = table.findOne(searchQuery);
        return result != null;
    }

    @Override
    public void save(String text, String translation) {
        BasicDBObject document = new BasicDBObject();
        document.put("text", text);
        document.put("translation", translation);
        table.insert(document);
    }

    @Override
    public String translation(String text) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("text", text);
        DBObject one = table.findOne(searchQuery);
        return (String) one.get("translation");
    }

    @Override
    public String getDBName() {
        return DBName;
    }

}
