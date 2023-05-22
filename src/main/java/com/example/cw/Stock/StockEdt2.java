package com.example.cw.Stock;

import com.example.cw.Home;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
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

public class StockEdt2 extends Stock{
    @FXML
    public TextField UnitPrz;
    @FXML
    public TextField EditQTY;
    @FXML
    public TextField PrdctNm;
    @FXML
    public TextField EditSupplier;
    @FXML
    public TextField StockID;

    public void EditStck() {
        BasicDBObject doc = new BasicDBObject("ID", StockID.getText());
        FindIterable<Document> findIterable = StockColl.find(doc);
        for (Document DOC : findIterable){
            BasicDBObject basicDBObject2 = new BasicDBObject();
            if (!Objects.equals(PrdctNm.getText(), "")) {
                basicDBObject2.put("ProductName", PrdctNm.getText().toUpperCase());
            }if (!Objects.equals(UnitPrz.getText(), "")) {
                basicDBObject2.put("UnitPrize", UnitPrz.getText());
            }if (!Objects.equals(EditQTY.getText(),"")) {
                basicDBObject2.put("Quantity", EditQTY.getText());
            }if (!Objects.equals(EditSupplier.getText(),"")) {
                basicDBObject2.put("Supplier", EditSupplier.getText());
            }if (!Objects.equals(EditQTY.getText(),"") || !Objects.equals(UnitPrz.getText(), "")) {
                if (!Objects.equals(EditQTY.getText(),"")) {
                    String Total = String.valueOf(DOC.getInteger("UnitPrize")*Integer.parseInt(EditQTY.getText()));
                    basicDBObject2.put("TotalPrice", Total);
                }
                if (!Objects.equals(UnitPrz.getText(), "")) {
                    String Total = String.valueOf(DOC.getInteger("Quantity")*Integer.parseInt(UnitPrz.getText()));
                    basicDBObject2.put("TotalPrice", Total);
                }
            }
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", basicDBObject2);
            StockColl.updateOne(DOC, updateObject);
        }
        Error.statics("Stock Edited");
        PrdctNm.setText("");
        UnitPrz.setText("");
        EditQTY.setText("");
        EditSupplier.setText("");

    }


    public void SetStock() {
        ArrayList<String> num = Find.findAll("ID",StockColl);
        ArrayList<String> name = Find.findAll("ProductName",StockColl);
        if (Objects.equals(StockID.getText(), "")) {
            Error.error("Enter the id of the product you want to edit first and press the set button");
        }
        else if (num.contains(StockID.getText())) {
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(StockColl, StockID.getText(), "ID", "ProductName")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ID", "UnitPrize")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ID", "Quantity")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ID", "Supplier")).get(0));
            PrdctNm.setText(set.get(0));
            UnitPrz.setText(set.get(1));
            EditQTY.setText(set.get(2));
            EditSupplier.setText(set.get(3));
        }else if (name.contains(StockID.getText().toUpperCase())){
            ArrayList<String> set = new ArrayList<>();
            set.add((Find.find(StockColl, StockID.getText(), "ProductName", "ProductName")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ProductName", "UnitPrize")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ProductName", "Quantity")).get(0));
            set.add((Find.find(StockColl, StockID.getText(), "ProductName", "Supplier")).get(0));
            PrdctNm.setText(set.get(0));
            UnitPrz.setText(set.get(1));
            EditQTY.setText(set.get(2));
            EditSupplier.setText(set.get(3));
        }else {
            Error.error("This ID or ProductName doesnt exists");
        }

    }
    public void Back(ActionEvent event) throws IOException {

        window.closeWindow(event);
        iterate();
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Stock.fxml"));
        window.newWindow(fxmlLoader);
    }
}
