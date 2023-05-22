package com.example.cw.Product;

import com.example.cw.Home;
import com.example.cw.LoginController;
import com.example.cw.User;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Product implements Initializable{
    @FXML
    public TableView<tblPrdkt> table;
    @FXML
    public TableColumn<tblPrdkt, String> clmid;
    @FXML
    public TableColumn<tblPrdkt, String> Qty;
    @FXML
    public TableColumn<tblPrdkt, String> clmname;
    @FXML
    public TableColumn<tblPrdkt, String> clmnCtgry;
    @FXML
    public TableColumn<tblPrdkt, String> clmDes;
    @FXML
    public TextField searchID;


    ObservableList<tblPrdkt> All = FXCollections.observableArrayList();
    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> product = dbConnection.getMongoCollection("Product");

    public ObservableList<tblPrdkt> iterate() {

        ObservableList<tblPrdkt> List = FXCollections.observableArrayList();
        FindIterable<Document> Find = product.find();

        for (Document DOC : Find) {
            tblPrdkt tblPrdkt1 = new tblPrdkt(DOC.getString("ID"),DOC.getString("Quantity"),DOC.getString("ProductName"), DOC.getString("CategoryName"), DOC.getString("description"));
            All.add(tblPrdkt1);
            tblPrdkt1.setClmid(DOC.getString("ID"));
            tblPrdkt1.setClmname(DOC.getString("ProductName"));
            tblPrdkt1.setClmDes(DOC.getString("description"));
            tblPrdkt1.setClmnCtgry(DOC.getString("CategoryName"));
            tblPrdkt1.setQty(DOC.getString("Quantity"));
            List.add(tblPrdkt1);

        }
        return List;
    }
    ProductDelet productDelet = new ProductDelet();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            productDelet.IDset(product);
            iterate();
            table.setItems(iterate());

            clmid.setCellValueFactory(new PropertyValueFactory<>("clmid"));
            Qty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
            clmname.setCellValueFactory(new PropertyValueFactory<>("clmname"));
            clmnCtgry.setCellValueFactory(new PropertyValueFactory<>("clmnCtgry"));
            clmDes.setCellValueFactory(new PropertyValueFactory<>("clmDes"));

        }catch (MongoTimeoutException timeoutException){
            Error.error("Errors Will generate cause of mongodb database not found");
        }

    }

    public void logout(ActionEvent event) throws IOException {
        window.closeWindow(event);

        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("LoginPage.fxml"));
        window.newWindow(fxmlLoader);
    }

    public void home(ActionEvent event) throws IOException {
        window.closeWindow(event);

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home.fxml"));
        window.newWindow(fxmlLoader);
    }

    public void NewProduct(ActionEvent event) throws IOException {
        window.closeWindow(event);
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("ProductNew.fxml"));
        window.newWindow(fxmlLoader);
    }

    public void EditProduct(ActionEvent event) throws IOException {
        Error.warning("Enter the id of the product you want to edit first and press the set button");

        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("ProductEdit.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void DeletProduct(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("ProductDelet.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

}

