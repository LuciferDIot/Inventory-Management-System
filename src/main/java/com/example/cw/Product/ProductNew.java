package com.example.cw.Product;

import com.example.cw.Home;
import com.mongodb.client.MongoCollection;
import functions.Error;
import functions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductNew implements Initializable{
    @FXML
    public TextField NProduct;
    public TextArea ProDescription;
    public TextField Quantity;
    public ChoiceBox<String> Dropbox;


    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> Categories = dbConnection.getMongoCollection("Categories");



    public void EnterProduct() {

        String NewProduct = NProduct.getText();
        String PrDescription = ProDescription.getText();
        String chicebox = Dropbox.getValue();
        try {
            String IntQua = Quantity.getText();
            MongoCollection<Document> product = dbConnection.getMongoCollection("Product");

            ArrayList<String> ProductList = Find.findAll("ProductName", product);


            if (!ProductList.contains(NewProduct.toUpperCase())){
                if (NewProduct.equals("") || Objects.equals(PrDescription, "") || IntQua.equals("")) {
                    Error.error("Some fields are empty");
                }

                else {
                    Document document = new Document();
                    document.put("ProductName", NProduct.getText().toUpperCase());
                    document.put("description", ProDescription.getText());
                    document.put("ID",  String.valueOf(ProductList.size()+1));
                    document.put("CategoryName", chicebox);
                    document.put("Quantity", IntQua);
                    product.insertOne(document);
                    Error.statics("Product Added");
                    settext();
                }
            }else {
                Error.error("Product already Exists");
            }

        } catch (Exception exc) {
            Error.error("Qty must be number");
        }

    }

    public void Back (ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Product.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void settext() {
        NProduct.setText("");
        ProDescription.setText("");
        Quantity.setText("");
        Dropbox.setValue("NONE");
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
