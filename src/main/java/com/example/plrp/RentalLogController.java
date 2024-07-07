package com.example.plrp;

import com.example.plrp.impl.RentalImpl;
import com.example.plrp.pojos.Rental;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RentalLogController implements Initializable {

    @FXML
    private TableView<Rental> table;
    @FXML
    private TableColumn<Rental, String> rentalId;
    @FXML
    private TableColumn<Rental, String> sessionId;
    @FXML
    private TableColumn<Rental, String> name;
    @FXML
    private TableColumn<Rental, String> tel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            RentalImpl rentalImpl = new RentalImpl();
            table.getItems().addAll(FXCollections.observableList(rentalImpl.retrieve()));
            rentalId.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getId()).asString());
            sessionId.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getSessionId()).asString());
            name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
            tel.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTel()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
