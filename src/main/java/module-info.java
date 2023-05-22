module com.example.cw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires mongo.java.driver;
    requires jdk.javadoc;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.cw to javafx.fxml;
    exports com.example.cw;
    exports functions;
    opens functions to javafx.fxml;
    exports com.example.cw.Categories;
    opens com.example.cw.Categories to javafx.fxml;
    exports com.example.cw.Product;
    opens com.example.cw.Product to javafx.fxml;
    exports com.example.cw.Stock;
    opens com.example.cw.Stock to javafx.fxml;
}