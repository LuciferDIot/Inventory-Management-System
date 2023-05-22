package com.example.cw.Categories;

import com.example.cw.Home;
import com.mongodb.BasicDBObject;
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

public class CategoriesDelet implements Initializable {
    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> CategoriesCollec = dbConnection.getMongoCollection("Categories");

    MongoCollection<Document> Product = dbConnection.getMongoCollection("Product");

    @FXML
    public TextField DCategory;

    @FXML
    public TextField CategoryID;
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Categories.fxml"));
        window.newWindow(fxmlLoader);
        IDset(CategoriesCollec);

        window.closeWindow(event);
    }

    public void DeletCategory() {

        String DltCategory = DCategory.getText();
        String ID = CategoryID.getText();

        ArrayList<String> CategoryList = Find.find(CategoriesCollec,DltCategory,"CategoryName","ID");
        ArrayList<String> CategoryList2 = Find.findAll("CategoryName", CategoriesCollec);

        EditValue.Edit(DltCategory.toUpperCase(),Product,"CategoryName","NONE");


        if (!DltCategory.equals("") || !ID.equals("")){
            if (CategoryList2.contains(DltCategory.toUpperCase())) {
                if (CategoryList.contains(ID)){
                    Document document = new Document();
                    document.put("CategoryName", DCategory.getText().toUpperCase());
                    document.put("ID",  CategoryID.getText());
                    CategoriesCollec.deleteOne(document);
                    Error.statics("Category Deleted");
                    DCategory.setText("");
                    CategoryID.setText("");
                    IDset(CategoriesCollec);
                }
            }
            else {
                Error.error("Category not Exists");
            }
        }else {
            Error.error("Some fields are empty");
        }

    }
    public void IDset(MongoCollection<Document> collection) {
        ArrayList<String> count =Find.findAll("ID",collection);
        for (int i=0;i<count.size();i++) {
            BasicDBObject basicDBObject = new BasicDBObject("ID",count.get(i));
            BasicDBObject basicDBObject2 = new BasicDBObject();
            basicDBObject2.put("ID",String.valueOf(i+1));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2);
            CategoriesCollec.updateOne(basicDBObject, updateObject);}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IDset(CategoriesCollec);
    }

}
