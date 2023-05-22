package functions;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class window {
    public static void newWindow(FXMLLoader fxmlLoader) throws IOException {
        Stage addContactStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        addContactStage.setScene(scene);
        addContactStage.resizableProperty().setValue(false);
        addContactStage.initStyle(StageStyle.UNDECORATED);
        addContactStage.show();
    }

    public static void closeWindow(ActionEvent event){
        Stage firstStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        firstStage.close();
    }
}
