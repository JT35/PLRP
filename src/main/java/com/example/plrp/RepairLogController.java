package com.example.plrp;

import com.example.plrp.impl.RepairImpl;
import com.example.plrp.pojos.Repair;
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

public class RepairLogController implements Initializable {

    @FXML
    private TableView<Repair> table;
    @FXML
    private TableColumn<Repair, String> repairId;
    @FXML
    private TableColumn<Repair, String> itemId;
    @FXML
    private TableColumn<Repair, String> state;
    @FXML
    private TableColumn<Repair, String> timestamp;
    @FXML
    private TableColumn<Repair, String> requestedBy;
    @FXML
    private TableColumn<Repair, String> comments;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            RepairImpl repairImpl = new RepairImpl();
            table.getItems().addAll(FXCollections.observableList(repairImpl.retrieve()));
            repairId.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getId()).asString());
            itemId.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getItemId()));
            state.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getState().toString()));
            timestamp.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTimestamp().toString()));
            requestedBy.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRequester()));
            comments.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getComments()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
