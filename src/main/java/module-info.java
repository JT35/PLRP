module com.example.plrp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.plrp to javafx.fxml;
    exports com.example.plrp;
}