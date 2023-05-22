package com.example.cw;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.ListDoc;
import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    @FXML
    public TextField NUsername;
    public TextField NPassword;
    public TextField CNPassword;

    @FXML //labels
    public Label PasswordID1;
    public Label IncUserName;
    public Label PasswordID2;

    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> collection = dbConnection.getMongoCollection("Users");

    ListDoc add = new ListDoc();

    ArrayList<String> UserNameList = new ArrayList<>();
    ArrayList<String> PasswordList = new ArrayList<>();

    public void Back (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("home.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }

    public void EnterUser() {
        label("","","");
        String NewUser = NUsername.getText();
        String NewPass = NPassword.getText();
        String ConPass = CNPassword.getText();

        BasicDBObject doc = new BasicDBObject("Name", NewUser.toUpperCase());
        find(UserNameList, PasswordList, doc, collection);
        if (!UserNameList.contains(NewUser.toUpperCase())){
            if ((NewUser.toUpperCase()).contains(NewPass.toUpperCase())
                    || (ConPass.toUpperCase()).contains(NewUser.toUpperCase())){
                if (NewPass.equals("") || ConPass.equals("")){
                    Error.error("Password Field is empty");
                }else {
                    label("", "This can be easily guessed","");
                }
            }
            else if(!Objects.equals(ConPass, NewPass)){
                label("", "", "Don't match Passwords");
                text(NewUser,"","");
            }
            else if (NewUser.length()<=5 || NewPass.length()<=5){
                if (NewUser.length()<=5){
                    label("Username must be at least 6 letters", "","");
                    text("",NewPass,ConPass);
                }else {
                    label("", "Weak Password", "");
                    text(NewUser,"","");
                }
            }
            else {
                //connect to mongoDB
                add.AddOrRemoveCollection(NewUser.toUpperCase(), ConPass, collection,"User Added Successfully",1);
                label("", "", "");
                text("","","");
            }
        }else {
            label("Username already Exists","","");
            text("","","");
        }
    }

    public void delet() {
        String NewUser = NUsername.getText();
        String NewPass = NPassword.getText();
        String ConPass = CNPassword.getText();
        label("", "", "");

        BasicDBObject doc = new BasicDBObject("Name", NewUser.toUpperCase());
        find(UserNameList, PasswordList, doc, collection);

        if (UserNameList.contains(NewUser.toUpperCase())){
            if (Objects.equals(NewPass, ConPass)){
                if (PasswordList.contains(ConPass)){
                    add.AddOrRemoveCollection(NewUser.toUpperCase(), ConPass, collection,"User's password DELETED successfully",2);
                    text("","","");

                }else {
                    Error.error("password not equal to user's password");
                    text(NewUser,"","");
                }
            }else {
                label("", "", "Don't match Passwords");
                text(NewUser,"","");
            }
        } else {
            Error.error("Username can't find");
            text("","","");
        }
    }

    // functions
    public void find(ArrayList<String> userNameList,
                     ArrayList<String> passwordList,
                     BasicDBObject doc,
                     MongoCollection<Document> collection) {
        FindIterable<Document> findIterable = collection.find(doc);
        FindIterable<Document> FindIterable = collection.find();
        for (Document DOC : FindIterable) {
            userNameList.add((String) DOC.get("Name"));}

        for (Document document : findIterable) {
            passwordList.add((String) document.get("Password"));
        }
    }

    public  void text(String Username, String PassField1, String PassField2) {
        NUsername.setText(Username);
        NPassword.setText(PassField1);
        CNPassword.setText(PassField2);
    }
    public void label(String Username, String PassField1, String PassField2) {
        IncUserName.setText(Username);
        PasswordID1.setText(PassField1);
        PasswordID2.setText(PassField2);
    }
}
