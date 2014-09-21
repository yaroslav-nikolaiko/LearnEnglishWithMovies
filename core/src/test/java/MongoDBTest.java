import com.mongodb.*;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * Created by yaroslav on 9/21/14.
 */
public class MongoDBTest {

    @Test
    @Ignore
    public void connection() throws Exception {
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("my-test");

        DBCollection table = db.getCollection("content");

        BasicDBObject document = new BasicDBObject();
        document.put("id", 1);
        document.put("content", new byte[]{1,23,4});
        //table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", 1);

/*        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", document);*/
        //upsert = true-> inserts a document if no document matches the update query criteria
        //multi = false -> update only one document (if true -> update all that founded by SearchQuery)
        table.update(searchQuery, document, true, false);


        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

/*        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id",1);
        //table.remove(searchQuery);

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext())
            System.out.println(cursor.next());*/
    }
}
