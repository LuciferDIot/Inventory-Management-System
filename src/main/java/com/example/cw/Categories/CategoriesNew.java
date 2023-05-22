package com.example.cw.Categories;

import com.example.cw.Home;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.Find;
import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CategoriesNew {
    @FXML
    public TextField NCategory;
    public TextField description;



    DBConnection dbConnection = DBConnection.getConnection();

    public void EnterCategory() {

        String NewCategory = NCategory.getText();
        String Description = description.getText();
        MongoCollection<Document> Categories = dbConnection.getMongoCollection("Categories");

        ArrayList<String> CategoryList = Find.findAll("CategoryName", Categories);

        if (!CategoryList.contains(NewCategory.toUpperCase())){
            if (NewCategory.equals("") || Objects.equals(Description, "")) {
                Error.error("Some fields are empty");
            }

            else if (NewCategory.length()<=3){
                Error.error("Category Name must be at least 4 letters");
            }
            else {
                Document document = new Document();
                document.put("CategoryName", NCategory.getText().toUpperCase());
                document.put("Description",description.getText());
                document.put("ID",  String.valueOf(CategoryList.size()+1));
                Categories.insertOne(document);
                Error.statics("Category Added");
                NCategory.setText("");
                description.setText("");
            }
        }else {
            Error.error("Category already Exists");
        }

    }

    public void Back (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Categories.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }

}
