package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.pojos.Item;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private TextField searchPrompt;
    @FXML
    private ChoiceBox filterOptions;

    @FXML
    private Text matchingResultCount;
    @FXML
    private TableView<Item> matchingResultsTable;
    @FXML
    private TableColumn<Item, String> mId;
    @FXML
    private TableColumn<Item, String> mModel;
    @FXML
    private TableColumn<Item, String> mPowerOutput;
    @FXML
    private TableColumn<Item, String> mSignedOut;
    @FXML
    private TableColumn<Item, String> mWorking;
    @FXML
    private TableColumn<Item, String> mAvailable;
    @FXML
    private TableColumn<Item, String> mRegistered;

    @FXML
    private Text remainingResultCount;
    @FXML
    private TableView<Item> remainingResultsTable;
    @FXML
    private TableColumn<Item, String> rId;
    @FXML
    private TableColumn<Item, String> rModel;
    @FXML
    private TableColumn<Item, String> rPowerOutput;
    @FXML
    private TableColumn<Item, String> rSignedOut;
    @FXML
    private TableColumn<Item, String> rWorking;
    @FXML
    private TableColumn<Item, String> rAvailable;
    @FXML
    private TableColumn<Item, String> rRegistered;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterOptions.getItems().addAll("None", "Signed Out", "Working", "Available");
        filterOptions.getSelectionModel().select(0);

        onViewClick(); // Init the table display
        populateCellValueFactories(mId, mModel, mPowerOutput, mSignedOut, mWorking, mAvailable, mRegistered);
        populateCellValueFactories(rId, rModel, rPowerOutput, rSignedOut, rWorking, rAvailable, rRegistered);
        allowForRightClick(mId);
        allowForRightClick(rId);
    }

    private void populateCellValueFactories(TableColumn<Item, String> mId, TableColumn<Item, String> mModel, TableColumn<Item, String> mPowerOutput, TableColumn<Item, String> mSignedOut, TableColumn<Item, String> mWorking, TableColumn<Item, String> mAvailable, TableColumn<Item, String> mRegistered) {
        mId.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getId()));
        mModel.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getModel()));
        mPowerOutput.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getPowerOutput())));
        mSignedOut.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().isSignedOut()).asString());
        mWorking.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().isWorking()).asString());
        mAvailable.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().isAvailable()).asString());
        mRegistered.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTimeRegistered().toString()));
    }

    private void allowForRightClick(TableColumn<Item, String> idColumn) {
        idColumn.setCellFactory(_ -> {
            final TableCell<Item, String> cell = new TableCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(cell.getTableRow().getItem().getId());
                    clipboard.setContent(content);
                }
            });
            return cell;
        });
    }

    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    @FXML
    protected void onViewClick() {
        matchingResultsTable.getItems().clear();
        remainingResultsTable.getItems().clear();

        ItemImpl itemImpl = new ItemImpl();

        if (!searchPrompt.getText().isEmpty()) {
            searchPrompt.getText().replace("'", "''");
            try {
                switch (filterOptions.getSelectionModel().getSelectedIndex()) {
                    case 0 -> populateTables(itemImpl.retrieveWithBarcode(searchPrompt.getText()), itemImpl.retrieveExceptBarcode(searchPrompt.getText()), itemImpl);
                    case 1 -> populateTables(itemImpl.retrieveWithFilter(searchPrompt.getText(), "SignedOut", true), itemImpl.retrieveExceptFilter(searchPrompt.getText(), "SignedOut", true), itemImpl);
                    case 2 -> populateTables(itemImpl.retrieveWithFilter(searchPrompt.getText(), "Working", true), itemImpl.retrieveExceptFilter(searchPrompt.getText(), "Working", true), itemImpl);
                    case 3 -> populateTables(itemImpl.retrieveWithTwoFilters(searchPrompt.getText(), "SignedOut", false, "Working", true), itemImpl.retrieveExceptTwoFilters(searchPrompt.getText(), "SignedOut", false, "Working", true), itemImpl);
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        } else {
            if (filterOptions.getSelectionModel().getSelectedIndex() == 0) {
                String query = "SELECT * FROM ITEM";
                try {
                    matchingResultsTable.getItems().addAll(FXCollections.observableList(itemImpl.retrieveAll(query)));
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            } else {
                String baseQuery = "SELECT * FROM ITEM WHERE ";
                String mQuery = baseQuery, rQuery = baseQuery;
                switch (filterOptions.getSelectionModel().getSelectedIndex()) {
                    case 1 -> {
                        mQuery += "SignedOut = TRUE";
                        rQuery += "SignedOut = FALSE";
                    }
                    case 2 -> {
                        mQuery += "Working = TRUE";
                        rQuery += "Working = FALSE";
                    }
                    case 3 -> {
                        mQuery += "SignedOut = FALSE AND Working = TRUE";
                        rQuery += "SignedOut = TRUE OR Working = FALSE";
                    }
                }
                try {
                    matchingResultsTable.getItems().addAll(FXCollections.observableList(itemImpl.retrieveAll(mQuery)));
                    remainingResultsTable.getItems().addAll(FXCollections.observableList(itemImpl.retrieveAll(rQuery)));
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }

        int numMResults = matchingResultsTable.getItems().size();
        int numRResults = remainingResultsTable.getItems().size();
        matchingResultCount.setText(numMResults + (numMResults == 1 ? " result" : " results"));
        remainingResultCount.setText(numRResults + (numRResults == 1 ? " result" : " results"));
    }

    private void populateTables(String itemImpl, String itemImpl1, ItemImpl itemImpl2) throws SQLException {
        String q1 = itemImpl;
        String q2 = itemImpl1;
        matchingResultsTable.getItems().addAll(FXCollections.observableList(itemImpl2.retrieveAll(q1)));
        remainingResultsTable.getItems().addAll(FXCollections.observableList(itemImpl2.retrieveAll(q2)));
    }

}