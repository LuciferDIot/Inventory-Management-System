package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

public class EditValue {
    public static void Edit(String DltCategory,
                     MongoCollection<Document> collection,
                     String ChangeField,
                     String to) {
        BasicDBObject basicDBObject = new BasicDBObject(ChangeField,DltCategory.toUpperCase());
        FindIterable<Document> findIterable = collection.find(basicDBObject);
        for (Bson DOC : findIterable) {
            BasicDBObject basicDBObject2 = new BasicDBObject();
            basicDBObject2.put(ChangeField,to);
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2);
            collection.updateOne(DOC, updateObject);
        }
    }
}
