package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public class Find {

    public static ArrayList<String> find (MongoCollection<Document> Collection, String gettest, String findBy, String findField) {
        ArrayList<String> CategoryList = new ArrayList<>();

        BasicDBObject doc = new BasicDBObject(findBy, gettest.toUpperCase());
        FindIterable<Document> FindIterable = Collection.find(doc);
        for (Document DOC : FindIterable) {
            CategoryList.add((String) DOC.get(findField));
        }
        return CategoryList;
    }

    public static ArrayList<String> findAll(String findField,MongoCollection<Document> Categories) {
        ArrayList<String> CategoryList2 = new ArrayList<>();

        FindIterable<Document> findIterable = Categories.find();
            try {
                for (Document DOC : findIterable) {
                    CategoryList2.add((String) DOC.get(findField));
                }
            }catch (MongoTimeoutException timeoutException){
                Error.error("Errors Will generate cause of mongodb database not found");
            }
        return CategoryList2;
    }
}
