package functions;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBConnection {
    private final MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    //static variables
    private static DBConnection dbConnection;

    //constance
    private static final String HOSTNAME = "Localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "DBMS";

    private DBConnection() {
        //instance variables
        MongoClient mongoClient = new MongoClient(HOSTNAME, PORT);
        mongoDatabase = mongoClient.getDatabase(DB_NAME);
    }

    public static DBConnection getConnection(){
        if(dbConnection == null){
            dbConnection =new DBConnection();
        }

        return dbConnection;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<Document> getMongoCollection(String collection) {
        mongoCollection = mongoDatabase.getCollection(collection);
        return mongoCollection;
    }

}
