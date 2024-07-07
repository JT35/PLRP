package com.example.plrp;

import com.example.plrp.impl.SessionImpl;
import com.example.plrp.pojos.Session;
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

public class SessionLogController implements Initializable {

    @FXML
    private TableView<Session> table;
    @FXML
    private TableColumn<Session, String> session;
    @FXML
    private TableColumn<Session, String> itemId;
    @FXML
    private TableColumn<Session, String> state;
    @FXML
    private TableColumn<Session, String> timestamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            SessionImpl sessionImpl = new SessionImpl();
            table.getItems().addAll(FXCollections.observableList(sessionImpl.retrieve()));
            session.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getId()).asString());
            itemId.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getItemId()));
            state.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getState().toString()));
            timestamp.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTimestamp().toString()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
