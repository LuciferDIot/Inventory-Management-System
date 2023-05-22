package functions;

import javafx.scene.control.Alert;

public class Error {
    public static void error(String error) {
        Alert lol = new Alert(Alert.AlertType.ERROR);
        lol.setTitle("ERROR DETECTED");
        lol.setContentText(error);
        lol.showAndWait();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ALERT");
    }
    public static void statics(String statics){
        Alert lol = new Alert(Alert.AlertType.CONFIRMATION);
        lol.setTitle("SUCCESSFULLY");
        lol.setContentText(statics);
        lol.showAndWait();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT");
    }

    public static void warning(String statics){
        Alert lol = new Alert(Alert.AlertType.INFORMATION);
        lol.setTitle("SUCCESSFULLY");
        lol.setContentText(statics);
        lol.showAndWait();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALERT");
    }
}
