package com.example.cw.Categories;

import com.example.cw.Home;
import com.example.cw.User;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Categories implements Initializable {
    @FXML
    public TableView<tblCtgry> table;
    @FXML
    public TableColumn<tblCtgry, String> clmid;
    @FXML
    public TableColumn<tblCtgry, String> clmname;
    @FXML
    public TableColumn<tblCtgry, String> clmDes;

    CategoriesDelet categoriesDelet = new CategoriesDelet();
    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> CategoriesCollec = dbConnection.getMongoCollection("Categories");


    public ObservableList<tblCtgry> iterate() {

        ObservableList<tblCtgry> List = FXCollections.observableArrayList();
        FindIterable<Document> FindIterable = CategoriesCollec.find();

        for (Document DOC : FindIterable) {
            tblCtgry tblCtgry = new tblCtgry();
            tblCtgry.setClmid(DOC.getString("ID"));
            tblCtgry.setClmname(DOC.getString("CategoryName"));
            tblCtgry.setClmDes(DOC.getString("Description"));
            List.add(tblCtgry);

        }
        return List;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            iterate();
            clmid.setCellValueFactory(new PropertyValueFactory<>("clmid"));
            clmname.setCellValueFactory(new PropertyValueFactory<>("clmname"));
            clmDes.setCellValueFactory(new PropertyValueFactory<>("clmDes"));

            table.setItems(iterate());

            categoriesDelet.IDset(CategoriesCollec);
        }catch (MongoTimeoutException timeoutException){
            Error.error("Errors Will generate cause of mongodb database not found");
        }
    }

    public void logout(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("LoginPage.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void home(ActionEvent event) throws IOException {
        window.closeWindow(event);
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home.fxml"));
        window.newWindow(fxmlLoader);
    }


    public void NewCategorie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("CategoriesNew.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void EditCategorie(ActionEvent event) throws IOException {
        Error.warning("Enter Category ID or Category Name to search Field and CLICK SET button to get others to text field and edit others");

        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("CategoriesEdit.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void DeletCategorie(ActionEvent event) throws IOException {
        window.closeWindow(event);
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("CategoriesDelet.fxml"));
        window.newWindow(fxmlLoader);
    }
}
