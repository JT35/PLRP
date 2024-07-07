package com.example.plrp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class LogsController {
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }
}
