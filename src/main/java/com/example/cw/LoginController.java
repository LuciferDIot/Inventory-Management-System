package com.example.cw;

import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import functions.DBConnection;
import functions.Error;
import functions.Find;
import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    public PasswordField password;

    @FXML
    public TextField username;

    @FXML
    public Button close_id;

    @FXML
    public Label wrong;
    public String user;
    public String passw;
    DBConnection dbConnection = DBConnection.getConnection();
    MongoCollection<Document> collection = dbConnection.getMongoCollection("Users");

    public void close(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public void Singin(ActionEvent event) throws IOException {
        user = username.getText();
        passw = password.getText();


        try {

            ArrayList<String> PasswordList = Find.find(collection, user, "Name", "Password");
            ArrayList<String> UserNameList = Find.findAll("Name", collection);

            if (user.equals("") || passw.equals("")) {
                Error.error("Username or Password Incomplete");
            }

            else if(!UserNameList.contains(user.toUpperCase())){
                Error.error("Username INVALID");
            }
            else if (!PasswordList.contains(passw)){
                Error.error("Password INVALID");
            }

            else {
                load(event);
            }
        }catch (MongoTimeoutException timeoutException){
            Error.error("Errors Will generate cause of mongodb database not found");
            load(event);
        }
    }

    private void load(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("home.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }
}
