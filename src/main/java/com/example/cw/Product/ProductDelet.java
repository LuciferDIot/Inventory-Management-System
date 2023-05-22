package com.example.cw.Product;

import com.example.cw.Home;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import functions.Error;
import functions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductDelet implements Initializable {

    @FXML
    public TextField DCategory;

    @FXML
    public TextField CategoryID;

    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> product = dbConnection.getMongoCollection("Product");

    public void DeletCategory() {
        String DltProduct = DCategory.getText();
        String ID = CategoryID.getText();

        ArrayList<String> productName = Find.findAll("ProductName",product);
        ArrayList<String> IDList = Find.find(product,DltProduct,"ProductName","ID");

        if (!DltProduct.equals("") || !ID.equals("")){
            if (productName.contains(DltProduct.toUpperCase())) {
                if (IDList.contains(ID)){
                    Document document = new Document();
                    document.put("ProductName", DltProduct.toUpperCase());
                    document.put("ID",  ID);
                    product.deleteOne(document);
                    Error.statics("Product Deleted");
                    DCategory.setText("");
                    CategoryID.setText("");
                    IDset(product);
                }
            }
            else {
                Error.error("Product not Exists");
            }
        }else {
            Error.error("Some fields are empty");
        }

    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Product.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);

    }
    public void IDset(MongoCollection<Document> collection) {

        ArrayList<String> count =Find.findAll("ID",collection);
        for (int i=0;i<count.size();i++) {
            BasicDBObject basicDBObject = new BasicDBObject("ID",count.get(i));
            BasicDBObject basicDBObject2 = new BasicDBObject();
            basicDBObject2.put("ID",String.valueOf(i+1));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2); // (3)
            collection.updateOne(basicDBObject, updateObject);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            IDset(product);
        }catch (MongoTimeoutException timeoutException){
            Error.error("Errors Will generate cause of mongodb database not found");
        }

    }
}
