package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.impl.RentalImpl;
import com.example.plrp.impl.SessionImpl;
import com.example.plrp.pojos.Item;
import com.example.plrp.pojos.Rental;
import com.example.plrp.pojos.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class SignoutController implements Initializable {

    private String scannedBarcode;

    private ItemImpl itemImpl;
    private SessionImpl sessionImpl;
    private RentalImpl rentalImpl;

    @FXML
    private TextField barcode;
    @FXML
    private TextField name;
    @FXML
    private TextField tel;

    public SignoutController(String barcode) {
        scannedBarcode = barcode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barcode.setText(scannedBarcode);
    }

    @FXML
    protected void onConfirmClick() throws IOException {
        if (name.getText().isBlank() && tel.getText().isBlank()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No data given! Provide either a name or phone number.");
            error.showAndWait();
            return;
        } else if (tel.getText().replace('-',' ')
                .replace('(', ' ')
                .replace(')', ' ')
                .strip().length() != 10)  {
            Alert error = new Alert(Alert.AlertType.ERROR, "Invalid phone number provided! Must be ten digits.");
            error.showAndWait();
            return;
        }

        try {
            float telValue = Float.parseFloat(tel.getText());

            sessionImpl = new SessionImpl();
            itemImpl = new ItemImpl();
            rentalImpl = new RentalImpl();

            Item findItem = itemImpl.retrieve(scannedBarcode);
            findItem.setSignedOut(!findItem.isSignedOut());
            itemImpl.update(findItem);

            Session session = new Session(findItem);
            session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            sessionImpl.create(session);

            Rental rental = new Rental(session.getId(), name.getText(), tel.getText());
            rentalImpl.create(rental);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Invalid phone number provided! Must be digits only.");
            error.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));

    }

    @FXML
    protected void onCancelClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("process-view.fxml"));
        fxmlLoader.setController(new ProcessController(scannedBarcode));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

}
