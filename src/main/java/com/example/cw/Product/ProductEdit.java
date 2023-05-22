package com.example.cw.Product;

import com.example.cw.Home;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.Find;
import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductEdit implements Initializable {
    public ChoiceBox<String> Dropbox;
    public TextField Quantity;
    public TextArea ProDescription;
    public TextField NProduct;
    public TextField ProID;
    public Button editbutton;

    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> ProductColl = dbConnection.getMongoCollection("Product");
    MongoCollection<Document> Categories = dbConnection.getMongoCollection("Categories");

    public void EditProduct() {
        if (!Objects.equals(ProID.getText(), "")){
            String chicebox = Dropbox.getValue();

            ArrayList<String> Name = Find.findAll("CategoryName", ProductColl);
            if (!(Name.contains(NProduct.getText().toUpperCase()))){

                BasicDBObject doc = new BasicDBObject("ID", ProID.getText());
                BasicDBObject basicDBObject2 = new BasicDBObject();
                if (!Objects.equals(NProduct.getText(), "")) {
                    basicDBObject2.put("ProductName",NProduct.getText().toUpperCase());
                }if (!Objects.equals(ProDescription.getText(), "")) {
                    basicDBObject2.put("Description",ProDescription.getText());
                }
                basicDBObject2.put("CategoryName",chicebox);
                if (!Objects.equals(Quantity.getText(), "")) {
                    basicDBObject2.put("Quantity", Quantity.getText());
                }
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", basicDBObject2);
                ProductColl.updateOne(doc, updateObject);
                Error.statics("Category Edited");
                NProduct.setText("");
                ProDescription.setText("");
                Dropbox.setValue("");
                Quantity.setText("");
            }

            else {
                Error.error("This Category name already exists");}
        }else {
            Error.error("Enter the id of the product you want to edit first and press the set button");
        }
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Product.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void Set() {
        ArrayList<String> num = Find.findAll("ID",ProductColl);
        ArrayList<String> name = Find.findAll("ProductName",ProductColl);
        if (Objects.equals(ProID.getText(), "")){
            Error.error("Search Field is empty");
        }else if (num.contains(ProID.getText())) {
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(ProductColl, ProID.getText(), "ID", "ProductName")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ID", "description")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ID", "CategoryName")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ID", "Quantity")).get(0));
            NProduct.setText(set.get(0));
            ProDescription.setText(set.get(1));
            Dropbox.setValue(set.get(2));
            Quantity.setText(set.get(3));
        }else if (name.contains(ProID.getText().toUpperCase())) {
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(ProductColl, ProID.getText(), "ProductName", "ProductName")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ProductName", "description")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ProductName", "CategoryName")).get(0));
            set.add((Find.find(ProductColl, ProID.getText(), "ProductName", "Quantity")).get(0));
            NProduct.setText(set.get(0));
            ProDescription.setText(set.get(1));
            Dropbox.setValue(set.get(2));
            Quantity.setText(set.get(3));
        }
        else {
            Error.error("Enter the id of the product you want to edit first and press the set button");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> AllCategories3 = Find.findAll("CategoryName", Categories);
        AllCategories3.add("NONE");
        for (String allCategory : AllCategories3) {
            Dropbox.setValue("NONE");
            Dropbox.getItems().add(allCategory);
        }
    }
}
