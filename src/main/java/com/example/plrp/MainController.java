package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.pojos.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField barcodePrompt;

    @FXML
    protected void onProcessClick() throws IOException {
        if (validBarcode())
            return;

        String gotoView = "main-view.fxml";
        try {
            ItemImpl itemImpl = new ItemImpl();
            Item itemCheck = itemImpl.retrieve(barcodePrompt.getText());
            gotoView = itemCheck != null ? "process-view.fxml" : "register-or-update-view.fxml";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gotoView));
            fxmlLoader.setController(
                    gotoView.equals("process-view.fxml") ?  new ProcessController(barcodePrompt.getText()) :
                                                            new RegisterOrUpdateController(barcodePrompt.getText(), true));
            AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
        }
    }

    @FXML
    protected void onInventoryClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inventory-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    @FXML
    protected void onLogsClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logs-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    private boolean validBarcode() {
        String barcode = barcodePrompt.getText().strip();
        return barcode.isBlank();
    }

}