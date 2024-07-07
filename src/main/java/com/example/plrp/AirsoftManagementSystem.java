package com.example.plrp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class AirsoftManagementSystem extends Application {

    public static Stage primaryStage;
    public static Connection conn = DBConn.getConnection();
    public static final int SCR_W = 615;
    public static final int SCR_H = 500;

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(AirsoftManagementSystem.class.getResource("main-view.fxml"));
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Airsoft Management System");
        primaryStage.setScene(new Scene(fxmlLoader.load(), SCR_W, SCR_H));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}