package functions;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class ListDoc {
    public void AddOrRemoveCollection(String Name, String Password,
                                      MongoCollection<Document> collection ,
                                      String Display, int call)
    {
        Error.statics(Display);
        Document NEWUser = new Document();
        NEWUser.put("Name",Name);
        NEWUser.put("Password",Password);
        System.out.println(NEWUser);
        if (call == 1){
            collection.insertOne(NEWUser);
        }
        else if (call ==2) {
            collection.deleteOne(NEWUser);
        }
    }
    public void AddOrRemoveCategory(String Category, String Description, int ball, String ID,
                                    MongoCollection<Document> collection ,
                                    String Display, int call) {
        Error.statics(Display);
        Document NEWUser = new Document();
        NEWUser.put("CategoryName", Category);
        if (ball == 1){
            NEWUser.put("Description", Description);}
        else {
            NEWUser.put("ID",ID);
            System.out.println(NEWUser);
            if (call == 1) {
                collection.insertOne(NEWUser);
            } else if (call == 2) {
                collection.deleteOne(NEWUser);
            }
        }

    }

}
