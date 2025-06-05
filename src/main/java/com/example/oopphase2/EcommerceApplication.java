package com.example.oopphase2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class EcommerceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database database = new Database();
        FXMLLoader fxmlLoader = new FXMLLoader(EcommerceApplication.class.getResource("Login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml, Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(EcommerceApplication.class.getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
