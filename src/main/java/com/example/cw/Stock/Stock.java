package com.example.cw.Stock;

import com.example.cw.Home;
import com.example.cw.User;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.Find;
import functions.window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class Stock implements Initializable {
    public TableColumn<tblStock, String> Supplier;
    public TableColumn<tblStock, String> Updated;
    public TableColumn<tblStock, String> ProdName;
    public TableColumn<tblStock, String> Qty;
    public TableColumn<tblStock, String> UnitPrice;
    public TableColumn<tblStock, String> TotalPrice;
    public TableView<tblStock> tbl;
    public TextField ProNameTxt;
    public TextField UnitPriceTxt;
    public TextField QtyText;
    public TextField SupplierTxt;
    public TextField DeletTxt;

    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> StockColl = dbConnection.getMongoCollection("Stock");

    public ObservableList<tblStock> iterate() {

        ObservableList<tblStock> List = FXCollections.observableArrayList();
        FindIterable<Document> Find = StockColl.find();

        for (Document DOC : Find) {
            tblStock StockDoc = new tblStock();
            StockDoc.setUpdated(DOC.getString("ID"));
            StockDoc.setProdName(DOC.getString("ProductName"));
            StockDoc.setQty(DOC.getString("Quantity"));
            StockDoc.setUnitPrice(DOC.getString("UnitPrize"));
            StockDoc.setSupplier(DOC.getString("Supplier"));
            StockDoc.setTotalPrice(DOC.getString("TotalPrice"));
            List.add(StockDoc);

        }
        return List;
    }


    public void home(ActionEvent event) throws IOException {
        window.closeWindow(event);
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home.fxml"));
        window.newWindow(fxmlLoader);
    }

    public void logout(ActionEvent event) throws IOException {
        window.closeWindow(event);

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("LoginPage.fxml"));
        window.newWindow(fxmlLoader);
    }

    ArrayList<String> ProductList = Find.findAll("ProductName", StockColl);

    public void AddStock(ActionEvent event) {
        try {
        String ProductName = ProNameTxt.getText().toUpperCase();
        String UnitPrize = String.valueOf(Integer.parseInt(UnitPriceTxt.getText()));
        String Quantity = String.valueOf(Integer.parseInt(QtyText.getText()));
        String SupplierVari = SupplierTxt.getText();
        String TotalPrize = String.valueOf(Integer.parseInt(UnitPrize)*Integer.parseInt(Quantity));
        if (!ProductList.contains(ProductName.toUpperCase())) {
            if (!(ProductName.equals("") || Objects.equals(SupplierVari, "") || Quantity.equals("") || UnitPrize.equals(""))) {
                Error.statics("Product Stock Added");

                Document document = new Document();
                document.put("ID", String.valueOf(ProductList.size() + 1));
                document.put("ProductName", ProductName);
                document.put("UnitPrize", UnitPrize);
                document.put("Quantity", Quantity);
                document.put("TotalPrice",TotalPrize);
                document.put("Supplier", SupplierVari);
                StockColl.insertOne(document);
                window.closeWindow(event);

                reArrange();
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Stock.fxml"));
                window.newWindow(fxmlLoader);


            }else {
                Error.error("Some fields are empty");
            }
        } else {
            Error.error("Product Name is already exists");
        }
    } catch (NumberFormatException e) {
        Error.error("Quantity and UnitPrize must be Integer");
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Delet(ActionEvent event) throws IOException {

        String ID = DeletTxt.getText();

        ArrayList<String> productName = Find.findAll("ID",StockColl);

        if (!ID.equals("")){
            if (productName.contains(ID.toUpperCase())) {
                Document document = new Document();
                document.put("ID",  ID);
                StockColl.deleteOne(document);
                reArrange();
                window.closeWindow(event);
                Error.statics("Stock Deleted");
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Stock.fxml"));
                window.newWindow(fxmlLoader);
            }
            else {
                Error.error("Product not Exists");
            }
        }else {
            Error.error("ID field is empty");
        }


    }
    public void reArrange(){
        ArrayList<String> count =Find.findAll("ID",StockColl);
        for (int i=0;i<count.size();i++) {
            BasicDBObject basicDBObject = new BasicDBObject("ID",count.get(i));
            BasicDBObject basicDBObject2 = new BasicDBObject();
            basicDBObject2.put("ID",String.valueOf(i+1));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2); // (3)
            StockColl.updateOne(basicDBObject, updateObject);
        }
    }

    public void EditStock(ActionEvent event) throws IOException {
        window.closeWindow(event);

        Error.warning("Enter the relevant ID or Product Name that you want to edit and press the Set button search");

        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("StockEdit2.fxml"));
        window.newWindow(fxmlLoader);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reArrange();


            iterate();

            Updated.setCellValueFactory(new PropertyValueFactory<>("Updated"));
            ProdName.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
            Qty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
            UnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
            TotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
            Supplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));

            tbl.setItems(iterate());
        } catch (MongoTimeoutException timeoutException) {
            Error.error("Errors Will generate cause of mongodb database not found");
        } catch (Exception ignored){}

    }
}