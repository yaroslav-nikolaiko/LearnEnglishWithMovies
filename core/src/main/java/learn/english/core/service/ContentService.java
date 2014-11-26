package learn.english.core.service;

import com.mongodb.*;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by yaroslav on 9/21/14.
 */
@Stateless
@NoArgsConstructor
public class ContentService {
    private static final String ID = "item_id";
    private static final String COLLECTION = "content";
    private static final String CONTENT = "content";
    @Inject DB db;
    DBCollection table;

    @PostConstruct
    void init(){
        table = db.getCollection(COLLECTION);
    }

    public void update(Long itemId, byte[] content){
        BasicDBObject document = new BasicDBObject();
        document.put(ID, itemId);
        document.put(CONTENT, content);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, itemId);

        /********************************************************************************************
        upsert = true-> inserts a document if no document matches the update query criteria
        multi = false -> update only one document (if true -> update all that founded by SearchQuery)
        *********************************************************************************************/
        table.update(searchQuery, document, true, false);

    }

    public byte[] get(Long itemId){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, itemId);

        DBObject result = table.findOne(searchQuery);
        return (byte[]) result.get(CONTENT);
    }

    public void remove(Long itemId){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID, itemId);
        table.remove(searchQuery);
    }

}
