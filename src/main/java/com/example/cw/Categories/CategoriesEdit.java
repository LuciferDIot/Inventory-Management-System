package com.example.cw.Categories;

import com.example.cw.Home;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.Find;
import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CategoriesEdit {
    public TextField description;
    public TextField NCategory;
    public TextField EditID;

    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> Ctgry = dbConnection.getMongoCollection("Categories");

    public void EditCategory() {

        ArrayList<String> Name = Find.findAll("CategoryName",Ctgry);
        if (!(Name.contains(NCategory.getText().toUpperCase()))){

            BasicDBObject doc = new BasicDBObject("ID", EditID.getText());
            BasicDBObject basicDBObject2 = new BasicDBObject();
            if (!Objects.equals(NCategory.getText(), "")) {
                basicDBObject2.put("CategoryName",NCategory.getText().toUpperCase());
            } if (!Objects.equals(description.getText(), "")) {
                basicDBObject2.put("Description",description.getText());
            }
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2);
            Ctgry.updateOne(doc, updateObject);
            Error.statics("Category Edited");
            description.setText("");
            NCategory.setText("");
            EditID.setText("");
        }

        else {
            Error.error("This Category name already exists");
        }
    }

    public void Back(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Categories.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void Set() {
        ArrayList<String> num = Find.findAll("ID",Ctgry);
        ArrayList<String> name = Find.findAll("CategoryName",Ctgry);
        if (num.contains(EditID.getText())) {
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(Ctgry, EditID.getText(), "ID", "CategoryName")).get(0));
            set.add((Find.find(Ctgry, EditID.getText(), "ID", "Description")).get(0));
            NCategory.setText(set.get(0));
            description.setText(set.get(1));}
        else if (name.contains(EditID.getText().toUpperCase())) {
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(Ctgry, EditID.getText(), "CategoryName", "CategoryName")).get(0));
            set.add((Find.find(Ctgry, EditID.getText(), "CategoryName", "Description")).get(0));
            NCategory.setText(set.get(0));
            description.setText(set.get(1));
        }
        else {
            Error.error("Search field is empty");
        }

    }

}

