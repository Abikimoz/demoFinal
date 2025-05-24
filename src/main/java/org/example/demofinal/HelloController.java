package org.example.demofinal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.example.demofinal.config.dbConnect;

import java.sql.Connection;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        Connection connection = null;
        connection = dbConnect.getConnection();
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
