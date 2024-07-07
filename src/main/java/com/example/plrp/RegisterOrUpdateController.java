package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.pojos.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RegisterOrUpdateController implements Initializable {

    private boolean isRegistering;
    private String scannedBarcode;
    private String oldModel;
    private float oldPowerOutput;

    @FXML
    private Text header;
    @FXML
    private TextField barcode;
    @FXML
    private TextField model;
    @FXML
    private TextField powerOutput;
    @FXML
    private Button submitBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        header.setText(isRegistering ? "New Item" : "Edit Item");
        barcode.setText(scannedBarcode);
        if (!isRegistering) {
            try {
                ItemImpl itemImpl = new ItemImpl();
                Item item = itemImpl.retrieve(scannedBarcode);
                oldModel = item.getModel();
                oldPowerOutput = item.getPowerOutput();
                if (item != null) {
                    model.setText(oldModel);
                    powerOutput.setText(String.valueOf(oldPowerOutput));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        submitBtn.setText(isRegistering ? "Register" : "Update");
    }

    public RegisterOrUpdateController(String barcode, boolean registering) {
        scannedBarcode = barcode;
        isRegistering = registering;
    }

    @FXML
    protected void onSubmitClick() throws IOException {
        if (barcode.getText().isBlank() || model.getText().isBlank() || powerOutput.getText().isBlank()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Prompts cannot be blank!");
            error.showAndWait();
            return;
        }

        float powerOutputVal;
        try {
            // Simply make a float cast check to see if the provided power output is valid
            powerOutputVal = Float.parseFloat(powerOutput.getText());

            ItemImpl itemImpl = new ItemImpl();
            if (isRegistering) {
                try {
                    Item item = new Item(barcode.getText(), model.getText(), powerOutputVal);
                    item.setTimeRegistered(Timestamp.valueOf(LocalDateTime.now()));
                    itemImpl.create(item);
                    Alert message = new Alert(Alert.AlertType.INFORMATION, "Successfully registered item to database.");
                    message.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("process-view.fxml"));
                    fxmlLoader.setController(new ProcessController(barcode.getText()));
                    AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
                }
            } else {
                try {
                    if (!model.getText().equals(oldModel) || powerOutputVal != oldPowerOutput) {
                        Item newInfo = new Item(barcode.getText(), model.getText(), powerOutputVal);
                        itemImpl.update(newInfo);
                        Alert message = new Alert(Alert.AlertType.INFORMATION, "Successfully updated item in database.");
                        message.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("process-view.fxml"));
                    fxmlLoader.setController(new ProcessController(scannedBarcode));
                    AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
                }
            }
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Power Output must be a floating-point value!");
            error.showAndWait();
        }
    }

    @FXML
    protected void onCancelClick() throws IOException {
        String gotoView = isRegistering ? "main-view.fxml" : "process-view.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gotoView));
        if (!isRegistering)
            fxmlLoader.setController(new ProcessController(scannedBarcode));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

}
