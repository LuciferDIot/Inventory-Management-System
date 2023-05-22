package com.example.cw;

import functions.window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Home {

    @FXML

    public void log_out(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("LoginPage.fxml"));
        window.newWindow(fxmlLoader);
        window.closeWindow(event);
    }

    public void stock(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Stock.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }

    public void category(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Categories.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }

    public void product(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("Product.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }


    public void user(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("user.fxml"));
        window.newWindow(fxmlLoader);

        window.closeWindow(event);
    }
}
